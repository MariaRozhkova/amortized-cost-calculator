# Amortized cost calculator
Contains logic of calculating amortized inventory cost by month. Amortization is calculated using
linear method.


## Local run

Run service from IDE:
Run `AmortizedCostCalculator` from IDE with specified `inputFilePath` and `outputDir` command 
line args.
Result of application run is csv file stored in outputDir.

Run service from terminal:

build jar using gradle and then run:
```console
java -jar amortized-cost-calculator-1.0.jar --inputFilePath D:\input.csv --outputDir D:\output
```

### Supported command line args

**inputFilePath**(mandatory) - file path to input file.<br>
**outputDir**(mandatory) - path to directory where output file will be stored.<br>
**separator**(optional, default - ',') - column separator in csv file.<br>


## Notes
- Amortization is accrued from the month following the month when inventory was purchased. 
- Amortized cost is initial cost minus accumulated amortization for previous months. 
- Input csv file with inventories has to contain header with values in any order: name, purchase date,
  service period, price. All of them are required.
- If input file does not contain inventories, exception will be thrown.
- If result.csv file with amortized inventory cost by month results already exist, it will be overridden.
- Last row in result file contains info about total amortized cost by month for all inventories
