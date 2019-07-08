package service;

import model.Account;
import model.CurrencyType;
import model.User;
import utils.AccountUtil;
import utils.ApplicationConst;
import utils.TxtFileReader;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

public class AccountService {

    private User user;
    private static final Logger logger = Logger.getLogger(Logger.class.getName());

    public AccountService(User user) {
        this.user = user;
    }

    public void buildAccounts(){
        TxtFileReader txtFileReader = new TxtFileReader(ApplicationConst.ACCOUNTS_FILE_PATH);
        ArrayList<String> lines = txtFileReader.read();
        for (String line : lines) {
            String[] tokens = line.split(" ");
            if (tokens.length != 4){
                continue;
            }
            String accountId = tokens[0];
            if (!AccountUtil.isValidId(accountId)){
                continue;
            }
            String ownerName = tokens[1];
            if (!user.getUserId().equals(ownerName)){
                continue;
            }
            String accountBalanceStr = tokens[2];
            int accountBalance = Integer.parseInt(accountBalanceStr);
            if (accountBalance < 0){
                continue;
            }
            String currencyTypeStr = tokens[3];
            if (!AccountUtil.isValidCurrencyType(currencyTypeStr)){
                continue;
            }
            CurrencyType currencyType = AccountUtil.getCurrencyType(currencyTypeStr);
            Account account = new Account(ownerName,accountId,new BigDecimal(accountBalance),currencyType);
            user.addAccount(account);
        }
    }

    public void displayCurrentInfo(){
        logger.info(user.toString());
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nPress any key...");
        scanner.nextLine();
    }

    public Account createAccount() {
        Scanner scanner = new Scanner(System.in);
        boolean isValidAccount = false;
        String accountId = "";
        while (!isValidAccount) {
            System.out.print("\nAccount number (should start with 'RO' and should have 10 characters): ");
            accountId = scanner.nextLine();
            isValidAccount = AccountUtil.isValidId(accountId);
        }
        System.out.println("Amount of money is: ");
        String balanceStr = scanner.nextLine();
        BigDecimal balance = new BigDecimal(Integer.parseInt(balanceStr));
        System.out.println("Currency of your newly created account is: ");
        String currencyStr = scanner.nextLine();
        CurrencyType currency = AccountUtil.getCurrencyType(currencyStr);
        Account account = new Account(user.getUserId(), accountId, balance, currency);
        user.addAccount(account);

        return account;
    }
}
