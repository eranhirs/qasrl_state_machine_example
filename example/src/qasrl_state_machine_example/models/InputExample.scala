package qasrl_state_machine_example.models

trait InputExample {
  def getSentence: String

  def getPredicateIdx: Integer

  def getQuestion: String
}
