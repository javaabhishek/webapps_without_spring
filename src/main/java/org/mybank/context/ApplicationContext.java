package org.mybank.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.mybank.service.TransactionService;

public class ApplicationContext {
    public static final TransactionService transactionService=new TransactionService();
    public static final ObjectMapper objectMapper=new ObjectMapper();
}
