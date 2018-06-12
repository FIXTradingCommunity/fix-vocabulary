# fix-vocabulary

This project is a controlled vocabulary of business terms used in FIX Protocol. This a project of [FIX Trading Community](http://www.fixtradingcommunity.org/).

The vocabulary is intended to serve multiple purposes:

* A machine readable file for editing the vocabulary in the future, as opposed to text files.
* Humanly readable documentation can be generated in various forms.
* A semantic file can be queried in a SPARQL endpoint.
* Vocabulary terms can be linked to other financial industry vocabularies.
* Provides stable semantic concepts to inform message translators between different versions of FIX or between FIX and other protocols.

## Semantic notations

The vocabulary is encoding using semantic notations:

[Resource Description Framework (RDF)](https://www.w3.org/TR/2014/REC-rdf11-concepts-20140225/)

[Simple Knowledge Organization System (SKOS)](https://www.w3.org/TR/skos-reference/)

The file is serialized using [Turtle](https://www.w3.org/TR/turtle/) notation.

These notations may be read and edited using tools like [Protégé](https://protege.stanford.edu/).

## Presentation

For easy viewing of the semantic file, it is wrapped in a [Jupyter Notebook](https://jupyter.org/).

The [Gastrodon](https://github.com/paulhoule/gastrodon) toolkit enables the notebook to handle RDF data.

## Initial implementation

The initial vocabulary file was created by mechanical translation of FIX Glossary in FIX Protocol Version 5.0 Service Pack 2 Volume 1 with Errata 20110818. It has not yet been reviewed for quality.

## License
© Copyright 2018 FIX Protocol Limited

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.