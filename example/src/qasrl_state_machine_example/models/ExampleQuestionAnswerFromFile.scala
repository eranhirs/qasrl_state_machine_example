package qasrl_state_machine_example.models

case class ExampleQuestionAnswerFromFile(
                                   sentence: String,
                                   qasrl_id: String,
                                   verb_idx: Integer,
                                   verb: String,
                                   question: String,
                                   answer: String,
                                   answer_range: String,
                                   var wh: String = null,
                                   var aux: String = null,
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

  override def getQuestion: String = this.question
}
