## Scripts

### Shared Tool Functions
|File| Contents|
|:----|:-------|
|[generate-setting.py](./generateSetting.py)| generate problem setting to folder [data](../data), act as input for simulation|
|[pystats.py](./pystats.py)|  [Python statistic Tool](https://github.com/xizhonghua/pystats),process repetition results to get mean, variances, confidential interval etc|


### Simulation: Compare FirstFit, BestFit
|File| Contents|
|:----|:-------|
|[runSim.py](./runSim.py)| Simulations to evaluate FirstFit and BestFit, simulation with input from [data](../data), output to [result](../result)
|[processSim.py](./processSim.py)| process simulation result produced by [runSim.py](./runSim.py)|

- Parameter setting
  - set parameters in main function
  - TODO: using input arguments to set parameters would be better
- How to run
```
python runSim.py
python processSim.py
```

### Simulation: Compare OPT, FirstFit, BestFit
|File| Contents|
|:----|:-------|
|[oneOPTSimulation.py](./oneOPTSimulation.py)| Simulations to compare OPT, FirstFit and BestFit| 
|[processAvgOPT.py](./processAvgOPT.py)| Process the result of [oneOPTSimulation.py](./oneOPTSimulation.py) to get the average profit of OPT, FirstFit and BestFit|
|[copyFileSettingToLingo.bat](./copyFileSettingToLingo.bat)| copy settings from `./data` folder to lingo-opt folder, since Lingo requires the setting files to in the execution directory, this script will be called by [oneOPTSimulation.py](./oneOPTSimulation.py)|
- How to run
For each repeatition
  - step 1: `python oneOPTSimulation.py`
  - step 2: Run LINGO
  - step 3: `python processAvgOPT.py`
- Note: Since the LINGO we use is in free trail, the LINGO API we get has constraints on the size model, which prohibit our model from executing. Thus, we has to use manually execution. Those who want to run large scale simulations are suggested to buy a higher version of LINGO.

### Input Date
|File| Contents|
|:----|:-------|
|[realSolars.txt](./realSolars.txt)| Real solar trace from UMass Weather Station, acts as solar traces input of [generate-setting.py](./generate-setting.py)|






