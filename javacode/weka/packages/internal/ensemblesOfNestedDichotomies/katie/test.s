
[Data]
File = fold4.arff
TestSet = test4.arff
%XVal = 0

[Hierarchical]
Type = TREE
HSeparator = /
%ClassificationThreshold = [0.5, 0.75, 0.80, 0.90, 0.95]
SingleLabel = Yes

[Model]
MinimalWeight = 1.0


[Output]
AllFoldErrors = Yes
WritePredictions = Test

[Tree]
FTest= 0.05

[Ensemble]
Iterations = 10
EnsembleMethod = RForests
%PrintPaths = Yes
PrintAllModels = Yes
BagSize = 20
