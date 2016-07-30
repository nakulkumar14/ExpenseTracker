package com.tracker;

import com.tracker.dto.ExpenseDetailDTO;
import com.tracker.model.ExpenseDetail;
import com.tracker.services.ExpenseService;
import com.tracker.services.ExportService;
import com.tracker.services.MailSenderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class ExpenseController {

    private static Logger LOG = Logger.getLogger(ExpenseController.class);

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private ExportService exportService;

    @Autowired
    private MailSenderService mailSenderService;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "indexUI";
    }

    @RequestMapping(value = "getExpenseDetailsForToday", method = RequestMethod.GET)
    @ResponseBody
    public List<ExpenseDetail> getExpenseDetailsForToday() {
        LOG.info("Getting expense details for current day");
        List<ExpenseDetail> expenseDetails = expenseService.getAllForToday();
        return expenseDetails;
    }

    // Add expense detail
    @RequestMapping(value = "addExpense", method = RequestMethod.POST)
    @ResponseBody
    public boolean addExpense(@RequestBody ExpenseDetailDTO expenseDetailDTO) {
        LOG.info("Request to add description : " + expenseDetailDTO.getDescription());
        if (expenseDetailDTO == null || expenseDetailDTO.getDescription() == null || expenseDetailDTO.getAmount() == null)
            return false;
        return expenseService.addExpense(expenseDetailDTO);
    }

    @RequestMapping(value = "/deleteExpense/{id}", method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteExpense(@PathVariable("id") Long id) {
        LOG.info("Request to delete for id : " + id);
        boolean result = expenseService.delete(id);
        return result;
    }

    @RequestMapping(value = "getMonthlyExpenses", method = RequestMethod.POST)
    @ResponseBody
    public List<ExpenseDetail> getMonthlyExpenses(@RequestParam("month") String month, @RequestParam("year") String year) {
        List<ExpenseDetail> expenseDetails = expenseService.getMonthlyExpenses(month, year);
        return expenseDetails;
    }

    @RequestMapping(value = "getExpenses", method = RequestMethod.POST)
    @ResponseBody
    public List<ExpenseDetail> getExpenses(@RequestParam("date") String date) {
        LOG.info("Request received to get expenses  for date : " + date);
        List<ExpenseDetail> expenseDetails = expenseService.getExpenses(date);
        return expenseDetails;
    }

    @RequestMapping(value = "/editExpense/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ExpenseDetail editExpense(@PathVariable("id") Long id) {
        LOG.info("Request to edit record with id : " + id);
        ExpenseDetail expenseDetail = expenseService.getExpenseDetailById(id);
        LOG.info("Expense Detail for id : " + id + " : " + expenseDetail);
        return expenseDetail;
    }

    @RequestMapping(value = "updateExpenseDetail", method = RequestMethod.POST)
    @ResponseBody
    public boolean updateExpenseDetail(@RequestBody ExpenseDetailDTO expenseDetailDTO) {
        LOG.info("Updating expense detail : " + expenseDetailDTO);
        ExpenseDetail expenseDetail = expenseService.updateExpenseDetail(expenseDetailDTO);
        return expenseDetail != null ? true : false;
    }

    @RequestMapping(value = "getAllExpenseDetails", method = RequestMethod.GET)
    @ResponseBody
    public List<ExpenseDetail> getAllExpenseDetails() {
        List<ExpenseDetail> details = expenseService.getAllExpenseDetails();
        System.out.println(details);
        return details;
    }

    @RequestMapping(value = "exportToXLS", method = RequestMethod.POST)
    public String exportToXLS(@RequestParam("date") String date) {
//        try {
        //Prints the hostname i.e., machine name
//            LOG.info(InetAddress.getLocalHost().getHostName());
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
        LOG.info("Request to generate report for : " + date);
        List<ExpenseDetail> expenseDetails = expenseService.getExpenses(date);
        String absolutePathReport = exportService.exportToXLS(expenseDetails, date);
        if (!StringUtils.isEmpty(absolutePathReport)) {
            mailSenderService.sendMail("Nakul", "Report for " + date, absolutePathReport);
        }

        return "indexUI";
    }
}