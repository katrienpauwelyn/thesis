[Data]
File = /Users/katie/thesiscode/datasets/multilabelUpload/medical/medicaltrain.arff
TestSet = /Users/katie/thesiscode/datasets/multilabelUpload/medical/medicaltest.arff

[Hierarchical]
Type = TREE
HSeparator = /
SingleLabel = No
EmptySetIndicator = None

[Output]
AllFoldErrors = Yes
WritePredictions = Test

[Ensemble]
Iterations = 50
EnsembleMethod = Bagging
WriteBagTrainingFiles = Yes

[Model]
MinimalWeight = 333