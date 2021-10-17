package qasrl_state_machine_example.models

case class ExampleQuestionAnswerFromFile(
                                   sentence: String,
                                   qasrl_id: String,
                                   verb_idx: Integer,
                                   verb: String,
                                   question: String,
                                   answer: String,
                                   answer_range: String,
                                   var verbForm: Option[String] = None,
                                   var wh: String = null,
                                   var aux: String = null,
                                   var verb_prefix: String = null,
                                   var verb_slot_inflection: String = null,
                                   var subj: String = null,
                                   var obj: String = null,
                                   var prep: String = null,
                                   var obj2: String = null,
                                   var is_negated: Option[Boolean] = Option.empty,
                                   var is_passive: Option[Boolean] = Option.empty,
                                   var parse_succeeded: Boolean = false
                                 ) extends InputExample {
  override def getSentence: String = this.sentence

  override def getPredicateIdx: Integer = this.verb_idx

  override def getVerbForm: String = this.verbForm match {
    case None => this.getSentenceTokens(this.getPredicateIdx)
    case Some(verbalForm) => verbalForm 
  }

  override def getQuestion: String = this.question

  def as_invalid: ExampleQuestionAnswerFromFile = ExampleQuestionAnswerFromFile(
    sentence, qasrl_id, verb_idx, verb, "--Invalid Output--", "", "", verbForm
  )
}
