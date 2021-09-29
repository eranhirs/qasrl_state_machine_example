package qasrl_state_machine_example.models

case class ExampleFromCommandLine(sentence: String, predicate: Either[Int,String], question: String) extends InputExample {
  override def getSentence: String = this.sentence

  override def getPredicateIdx: Integer = this.predicate match {
    case Left(i) => i
    case Right(s) => 0
  }

  override def getQuestion: String = this.question

  override def getVerbForm: String = this.predicate match {
    case Left(idx) => this.getSentenceTokens(idx)
    case Right(verbalForm) => verbalForm 
  }
}