package qasrl_state_machine_example

import com.github.tototoshi.csv.{CSVReader, CSVWriter}
import jjm.implicits._
import qasrl.labeling.SlotBasedLabel
import qasrl.{Frame, QuestionProcessor, TemplateStateMachine}
import qasrl_state_machine_example.models.{ExampleFromCommandLine, ExampleQuestionAnswerFromFile, InputExample}

import java.nio.file.Paths


object RunStateMachine extends App {

  def readCommandLine(): List[InputExample] = {
    if (args.length > 0) {
      args(0) match {
        case "predict" => {
          if (args.length != 4) {
            println(f"Expecting exactly 3 arguments (sentence, predicate, predicted question) ; args.length ${args.length - 1}")
            return List()
          } else {
            return List(ExampleFromCommandLine(args(1), args(2).toInt, args(3)))
          }
        }
        case "file" => {
          if (args.length != 4) {
            println(f"Expecting exactly 3 arguments (inputFilePath, inputSentencesFilePath, outputFilePath) ; args.length ${args.length - 1}")
            return List()
          } else {
            return readFile(args(1), args(2))
          }
        }
        case _ => {
          println(f"Expected one of the following values: predict ; args(0) ${args(0)} ")
          return List()
        }
      }
    } else {
      println(f"No arguments received")
      return List()
    }
  }

  def readFile(inputFilePath: String, inputSentencesFilePath: String): List[InputExample] = {
    val reader = CSVReader.open(inputFilePath)
    val sentencesReader = CSVReader.open(inputSentencesFilePath)

    val sentencesMap: Map[String, String] = sentencesReader.allWithHeaders().map(rec =>
      rec("qasrl_id") -> rec("tokens")).toMap

    val records: List[InputExample] = (for (rec <- reader.allWithHeaders())
      yield ExampleQuestionAnswerFromFile(
        sentencesMap(rec("qasrl_id")),
        rec("qasrl_id"),
        rec("verb_idx").toInt,
        rec("verb"),
        rec("question"),
        rec("answer"),
        rec("answer_range")
      )
    )

    reader.close()
    sentencesReader.close()

    return records
  }

  def writeToFile(examples: List[InputExample]): Unit = {
    if (args.length > 0 && args(0) == "file") {
      val outputFilePath = args(3)

      val writer = CSVWriter.open(outputFilePath)
      writer.writeRow(List("qasrl_id","verb_idx","verb","question","answer","answer_range","wh","aux","subj","obj","prep","obj2","is_negated","is_passive"))
      writer.writeAll(
        for {
          example <- examples
          questionAnswer = example.asInstanceOf[ExampleQuestionAnswerFromFile]
        } yield {List(questionAnswer.qasrl_id, questionAnswer.verb_idx, questionAnswer.verb, questionAnswer.question, questionAnswer.answer, questionAnswer.answer_range, questionAnswer.wh, questionAnswer.aux, questionAnswer.subj, questionAnswer.obj, questionAnswer.prep, questionAnswer.obj2, questionAnswer.is_negated.getOrElse(""), questionAnswer.is_passive.getOrElse(""))}
      )

      writer.close()
    }
  }

  // Wiktionary data contains a bunch of inflections, used for the main verb in the QA-SRL template
  val wiktionaryPath = Paths.get("datasets/wiktionary")
  val wiktionary = new WiktionaryFileSystemService(wiktionaryPath)

  def parseExample(inputExample: InputExample): InputExample = {
    val exampleSentence = inputExample.getSentence
    val predicateIdx = inputExample.getPredicateIdx
    val question = inputExample.getQuestion

    val getSentenceTokens = (sentence: String) => sentence.split(" ").toVector

    // Inflections object stores inflected forms for all of the verb tokens seen in exampleSentence.iterator
    // normally you would throw all of your data into this iterator
    val tokens = getSentenceTokens(exampleSentence)
    val inflections = wiktionary.getInflectionsForTokens(tokens.iterator)
    val inflectedForms = inflections.getInflectedForms(tokens(predicateIdx).lowerCase).get

    // State machine stores all of the logic of QA-SRL templates and connects them to / iteratively constructs their Frames (see Frame.scala)
    val stateMachine = new TemplateStateMachine(tokens, inflectedForms)
    // Question processor provides a convenient interface for using the state machine to process a string
    val questionProcessor = new QuestionProcessor(stateMachine)

    val goodStatesOpt = questionProcessor.processStringFully(question.toString).toOption
    val slots = SlotBasedLabel.getSlotsForQuestion(tokens, inflectedForms, List(question.toString))
    for {
      slotOpt <- slots
      slot <- slotOpt
      goodStates <- goodStatesOpt
    } {
      val frame: Frame = goodStates.toList.collect {
        case QuestionProcessor.CompleteState(_, someFrame, _) => someFrame
      }.head
      val subj = slot.subj.getOrElse("")
      val aux = slot.aux.getOrElse("")
      val verbPrefix = slot.verbPrefix
      val obj = slot.obj.getOrElse("")
      val prep = slot.prep.getOrElse("")
      val obj2 = slot.obj2.getOrElse("")
      println(s"${slot.wh},$subj,$aux,$verbPrefix,${slot.verb},$obj,$prep,$obj2," +
        s"${frame.isPassive},${frame.isNegated},${frame.isProgressive},${frame.isPerfect}")

      if (inputExample.isInstanceOf[ExampleQuestionAnswerFromFile]) {
        val questionAnswer = inputExample.asInstanceOf[ExampleQuestionAnswerFromFile]
        questionAnswer.wh = slot.wh
        questionAnswer.subj = subj.toString
        questionAnswer.aux = aux.toString
        questionAnswer.obj = obj.toString
        questionAnswer.prep = prep.toString
        questionAnswer.obj2 = obj2.toString
        questionAnswer.is_negated = Option(frame.isNegated)
        questionAnswer.is_passive = Option(frame.isPassive)
        questionAnswer.parse_succeeded = true
      }
    }

    return inputExample
  }

  val inputExamples = readCommandLine()
  val parsedExamples: List[InputExample] = (for (inputExample <- inputExamples)
    yield parseExample(inputExample)
  )
  writeToFile(parsedExamples)

}
