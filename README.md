# QASRL State Machine Example

Easy to run example of the QASRL state machine from [Large-Scale QA-SRL Parsing](https://github.com/julianmichael/qasrl).

Based on [Controlled Crowdsourcing for High-Quality QA-SRL Annotation](https://github.com/plroit/qasrl-crowdsourcing/blob/ecbplus/qasrl-crowd-example/jvm/src/main/scala/example/RunQuestionParser.scala) fork.

## How to run single predict example

1. `docker pull hirscheran/qasrl_state_machine_example`
2. `docker run -it hirscheran/qasrl_state_machine_example "predict" "I really wanted to eat my ice cream before it melted , but I was busy presenting my poster ." 4 "what is someone eating?"`
   1. The number 4 indicates the predicate index in the sentence (counting from zero).

## How to run multiple examples with a csv file

Example `input_file.csv` and `input_sentences_file.csv` can be found in the `data` directory.

1. `docker pull hirscheran/qasrl_state_machine_example`
2. `docker run -it hirscheran/qasrl_state_machine_example "file" "input_file.csv" "input_sentences_file.csv" "output_file.csv"`

## Development

See original project or the Dockerfile for development instructions.

Build docker image: `docker build -t hirscheran/scala-mill .`

Push docker image: `docker push hirscheran/scala-mill`

## Citations


```
@article{
  DBLP:journals/corr/abs-1805-05377,
  author    = {Nicholas FitzGerald and
               Julian Michael and
               Luheng He and
               Luke Zettlemoyer},
  title     = {Large-Scale {QA-SRL} Parsing},
  journal   = {CoRR},
  volume    = {abs/1805.05377},
  year      = {2018},
  url       = {http://arxiv.org/abs/1805.05377},
  archivePrefix = {arXiv},
  eprint    = {1805.05377},
  timestamp = {Mon, 13 Aug 2018 16:47:46 +0200},
  biburl    = {https://dblp.org/rec/journals/corr/abs-1805-05377.bib},
  bibsource = {dblp computer science bibliography, https://dblp.org}
}
```


```
@inproceedings{roit-etal-2020-controlled,
    title = "Controlled Crowdsourcing for High-Quality {QA}-{SRL} Annotation",
    author = "Roit, Paul  and
      Klein, Ayal  and
      Stepanov, Daniela  and
      Mamou, Jonathan  and
      Michael, Julian  and
      Stanovsky, Gabriel  and
      Zettlemoyer, Luke  and
      Dagan, Ido",
    booktitle = "Proceedings of the 58th Annual Meeting of the Association for Computational Linguistics",
    month = jul,
    year = "2020",
    address = "Online",
    publisher = "Association for Computational Linguistics",
    url = "https://aclanthology.org/2020.acl-main.626",
    doi = "10.18653/v1/2020.acl-main.626",
    pages = "7008--7013",
    abstract = "Question-answer driven Semantic Role Labeling (QA-SRL) was proposed as an attractive open and natural flavour of SRL, potentially attainable from laymen. Recently, a large-scale crowdsourced QA-SRL corpus and a trained parser were released. Trying to replicate the QA-SRL annotation for new texts, we found that the resulting annotations were lacking in quality, particularly in coverage, making them insufficient for further research and evaluation. In this paper, we present an improved crowdsourcing protocol for complex semantic annotation, involving worker selection and training, and a data consolidation phase. Applying this protocol to QA-SRL yielded high-quality annotation with drastically higher coverage, producing a new gold evaluation dataset. We believe that our annotation protocol and gold standard will facilitate future replicable research of natural semantic annotations.",
}
```