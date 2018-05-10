CREATE TABLE new_loans (

id INTEGER not null, member_id INTEGER, loan_amnt DECIMAL, 
funded_amnt DECIMAL, funded_amnt_inv DECIMAL, term VARCHAR(32), 
int_rate VARCHAR(32), installment DECIMAL, grade VARCHAR(32), 
sub_grade VARCHAR(32), emp_title VARCHAR(64), emp_length VARCHAR(32), home_ownership VARCHAR(32), 
annual_inc DECIMAL, verification_status VARCHAR(32), issue_d VARCHAR(32), loan_status VARCHAR(32), pymnt_plan VARCHAR(32),
url VARCHAR(128), desc VARCHAR(128), purpose VARCHAR(32), title VARCHAR(32), zip_code VARCHAR(32), 
addr_state VARCHAR(32), dti DECIMAL, delinq_2yrs DECIMAL, 
earliest_cr_line VARCHAR(32), fico_range_low DECIMAL, fico_range_high DECIMAL, 
inq_last_6mths DECIMAL, mths_since_last_delinq VARCHAR(32), mths_since_last_record VARCHAR(32), 
open_acc DECIMAL, pub_rec DECIMAL, revol_bal DECIMAL, revol_util VARCHAR(32), total_acc DECIMAL, 
initial_list_status VARCHAR(32), out_prncp DECIMAL, out_prncp_inv DECIMAL, 
total_pymnt DECIMAL, total_pymnt_inv DECIMAL, total_rec_prncp DECIMAL, 
total_rec_int DECIMAL, total_rec_late_fee DECIMAL, recoveries DECIMAL, collection_recovery_fee DECIMAL, 
last_pymnt_d VARCHAR(32), last_pymnt_amnt DECIMAL, 
next_pymnt_d VARCHAR(32), last_credit_pull_d VARCHAR(32), last_fico_range_high DECIMAL,
last_fico_range_low DECIMAL, collections_12_mths_ex_med DECIMAL, mths_since_last_major_derog VARCHAR(32), 
policy_code DECIMAL, application_type VARCHAR(32), annual_inc_joint VARCHAR(32), dti_joint VARCHAR(32), 
verification_status_joint VARCHAR(32), acc_now_delinq DECIMAL, tot_coll_amt DECIMAL, tot_cur_bal DECIMAL, 
open_acc_6m DECIMAL, open_act_il DECIMAL, open_il_12m DECIMAL, open_il_24m DECIMAL, mths_since_rcnt_il INTEGER, 
total_bal_il DECIMAL, il_util DECIMAL, open_rv_12m DECIMAL, open_rv_24m DECIMAL, max_bal_bc DECIMAL, 
all_util DECIMAL, total_rev_hi_lim DECIMAL, inq_fi DECIMAL, total_cu_tl DECIMAL, inq_last_12m DECIMAL, 
acc_open_past_24mths DECIMAL, avg_cur_bal DECIMAL, bc_open_to_buy DECIMAL, bc_util DECIMAL, 
chargeoff_within_12_mths DECIMAL, delinq_amnt DECIMAL, mo_sin_old_il_acct DECIMAL, 
mo_sin_old_rev_tl_op DECIMAL, mo_sin_rcnt_rev_tl_op DECIMAL, mo_sin_rcnt_tl DECIMAL, 
mort_acc DECIMAL, mths_since_recent_bc DECIMAL, mths_since_recent_bc_dlq VARCHAR(32), 
mths_since_recent_inq VARCHAR(32), mths_since_recent_revol_delinq VARCHAR(32), num_accts_ever_120_pd DECIMAL,
num_actv_bc_tl DECIMAL, num_actv_rev_tl DECIMAL, num_bc_sats DECIMAL, num_bc_tl DECIMAL, 
num_il_tl DECIMAL, num_op_rev_tl DECIMAL, num_rev_accts DECIMAL, num_rev_tl_bal_gt_0 DECIMAL, 
num_sats DECIMAL, num_tl_120dpd_2m DECIMAL, num_tl_30dpd DECIMAL, num_tl_90g_dpd_24m DECIMAL, 
num_tl_op_past_12m DECIMAL, pct_tl_nvr_dlq DECIMAL, percent_bc_gt_75 DECIMAL, pub_rec_bankruptcies DECIMAL, 
tax_liens DECIMAL, tot_hi_cred_lim DECIMAL, total_bal_ex_mort DECIMAL, total_bc_limit DECIMAL, 
total_il_high_credit_limit DECIMAL, revol_bal_joint VARCHAR(32), sec_app_fico_range_low VARCHAR(32), 
sec_app_fico_range_high VARCHAR(32), sec_app_earliest_cr_line VARCHAR(32), sec_app_inq_last_6mths VARCHAR(32),
sec_app_mort_acc VARCHAR(32), sec_app_open_acc VARCHAR(32), sec_app_revol_util VARCHAR(32), 
sec_app_open_act_il VARCHAR(32), sec_app_num_rev_accts VARCHAR(32), 
sec_app_chargeoff_within_12_mths VARCHAR(32), sec_app_collections_12_mths_ex_med VARCHAR(32), 
sec_app_mths_since_last_major_derog VARCHAR(32), hardship_flag VARCHAR(32), hardship_type VARCHAR(32), 
hardship_reason VARCHAR(32), hardship_status VARCHAR(32), deferral_term VARCHAR(32), hardship_amount VARCHAR(32), 
hardship_start_date VARCHAR(32), hardship_end_date VARCHAR(32), payment_plan_start_date VARCHAR(32),
hardship_length VARCHAR(32), hardship_dpd VARCHAR(32), hardship_loan_status VARCHAR(32), 
orig_projected_additional_accrued_interest VARCHAR(32), hardship_payoff_balance_amount VARCHAR(32), 
hardship_last_payment_amount VARCHAR(32), disbursement_method VARCHAR(32), debt_settlement_flag VARCHAR(32), 
debt_settlement_flag_date VARCHAR(32), settlement_status VARCHAR(32), 
settlement_date VARCHAR(32), settlement_amount VARCHAR(32), settlement_percentage VARCHAR(32), settlement_term VARCHAR(32)
);
partition table new_loans on column id;

create table funds (
	fund_id integer, 
	fund_limit integer,
	primary key(fund_id)
);

create table FUNDS_RISK_DISTRIB (
	fund_id integer not null,
	risk integer not null,
	alloc_amt integer not null,
	primary key(fund_id, risk)
);
partition table FUNDS_RISK_DISTRIB on column risk;

create table inv_loans(
	loan_id integer not null,
	fund_id integer not null,
	risk integer not null,
	inv_amount integer not null,
	primary key(fund_id, loan_id, risk)
);
partition table inv_loans on column risk;

create table overflow_loans (
	loan_id integer not null
);

create stream overflow_loan_stream
	export to target loopback_OverflowLoan
(
	loan_id integer not null
);
--partition table overflow_loans on column loan_id;

create view FUNDS_INV_RISK as 
	 select fund_id, risk, sum(inv_amount) as total_inv, count(*) as loan_count from inv_loans group by fund_id, risk;

create stream LOAN_BY_RISK
	export to target loopback_AllocateLoan 
	(
		loan_id integer,
		loan_amt integer,
		prediction integer
	);

create procedure partition on table new_loans column id from class NewLoan;
create procedure partition on table new_loans column id from class NewLoan_ML;

create procedure from class UpdateFund;

create procedure partition on table funds_risk_distrib column risk from class AllocateLoan;

insert into funds values (1, 80000);
insert into funds values (2, 500000);
insert into funds values (3, 60000);
insert into funds values (4, 70000);
insert into funds values (5, 3000);
insert into funds values (6, 3000);

insert into funds_risk_distrib values (1, 0, 5000);
insert into funds_risk_distrib values (1, 1, 5000);
insert into funds_risk_distrib values (1, 2, 2000);
insert into funds_risk_distrib values (1, 3, 3000);
insert into funds_risk_distrib values (1, 4, 10000);
insert into funds_risk_distrib values (1, 5, 20000);
insert into funds_risk_distrib values (1, 6, 30000);
insert into funds_risk_distrib values (1, 7, 5000);

insert into funds_risk_distrib values (2, 0, 5000);
insert into funds_risk_distrib values (2, 1, 40000);
insert into funds_risk_distrib values (2, 2, 20000);
insert into funds_risk_distrib values (2, 3, 30000);
insert into funds_risk_distrib values (2, 4, 100000);
insert into funds_risk_distrib values (2, 5, 100000);
insert into funds_risk_distrib values (2, 6, 200000);
insert into funds_risk_distrib values (2, 7, 5000);

insert into funds_risk_distrib values (3, 0, 5000);
insert into funds_risk_distrib values (3, 1, 40000);
insert into funds_risk_distrib values (3, 2, 20000);
insert into funds_risk_distrib values (3, 3, 30000);
insert into funds_risk_distrib values (3, 4, 100000);
insert into funds_risk_distrib values (3, 5, 100000);
insert into funds_risk_distrib values (3, 6, 200000);
insert into funds_risk_distrib values (3, 7, 5000);

insert into funds_risk_distrib values (4, 0, 5000);
insert into funds_risk_distrib values (4, 1, 5000);
insert into funds_risk_distrib values (4, 2, 2000);
insert into funds_risk_distrib values (4, 3, 3000);
insert into funds_risk_distrib values (4, 4, 10000);
insert into funds_risk_distrib values (4, 5, 20000);
insert into funds_risk_distrib values (4, 6, 30000);
insert into funds_risk_distrib values (4, 7, 5000);