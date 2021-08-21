package qasrl_state_machine_example

import jjm.ling.en.{CountDictionary, Inflections, VerbInflectionDictionary}

import java.nio.file.Path

class WiktionaryFileSystemService(location: Path) {

  /** Constructs an Inflections object containing all known inflections
   * for a given set of words.
   *
   * @param tokens an iterator over all words we might want inflections for
   */
  def getInflectionsForTokens(tokens: Iterator[String]): Inflections = {
    val wiktionaryFilepath = location.resolve("en_verb_inflections.txt")
    val wordDict = new CountDictionary()
    tokens.foreach(wordDict.addString)
    val inflDict = new VerbInflectionDictionary(wordDict)
    inflDict.loadDictionaryFromFile(wiktionaryFilepath.toString)
    new Inflections(inflDict)
  }
}

