# LendingClub
Example of processing Lending Club data with VoltDB and H2O

Workflow -
1. Each new loan is passed to NewLoan procedure which calculates the risk and saves it to the NEW_LOANS table.
2. Some of the new loan's data is also written out to the LOAN_BY_RISK stream which is a loopback exporter back to VoltDB
3. The AllocateLoan procedure is called for each new loan written out to the LOAN_BY_RISK stream where a decision is made on the loan.
