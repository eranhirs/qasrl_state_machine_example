package qasrl_state_machine_example.models

case class ExampleFromCommandLine(sentence: String, predicateIdx: Integer, question: String) extends InputExample {
  override def getSentence: String = this.sentence

  override def getPredicateIdx: Integer = this.predicateIdx

  override def getQuestion: String = this.question
}