package com.tracker;

import com.google.gson.Gson;
import com.tracker.dto.ExpenseDetailDTO;
import com.tracker.model.ExpenseDetail;
import com.tracker.services.ExpenseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class ExpenseController {

    private static Logger LOG = Logger.getLogger(ExpenseController.class);

    @Autowired
    private ExpenseService expenseService;

    private Gson gson = new Gson();

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model) {
        LOG.info("Getting expense details for current day");
        List<ExpenseDetail> expenseDetails = expenseService.getAllForToday();
        LOG.info("Expense Details fetched : " + expenseDetails.size());
        model.addAttribute("expenseDetails", gson.toJson(expenseDetails));
        return "index";
    }

    @RequestMapping(value = "getExpenses", method = RequestMethod.POST)
    @ResponseBody
    public String getExpenses(@RequestParam("date") String date) {
        System.out.println("Request received : " + date);
        List<ExpenseDetail> expenseDetails = expenseService.getExpenses(date);
        return gson.toJson(expenseDetails);
    }

    @RequestMapping(value = "getMonthlyExpenses", method = RequestMethod.POST)
    @ResponseBody
    public String getMonthlyExpenses(@RequestParam("month") String month, @RequestParam("year") String year) {
        List<ExpenseDetail> expenseDetails = expenseService.getMonthlyExpenses(month, year);
        return gson.toJson(expenseDetails);
    }

    @RequestMapping(value = "addExpense", method = RequestMethod.POST)
    public String addExpense(@ModelAttribute("expenseDetailDTO") ExpenseDetailDTO expenseDetailDTO) {
        expenseService.addExpense(expenseDetailDTO);
        return "index";
    }

    @RequestMapping(value = "deleteByDescription", method = RequestMethod.POST)
    public String deleteByDescription(@RequestParam("description") String description) {
        System.out.println("Description to delete : " + description);
        expenseService.deleteByDescription(description);
        return "index";
    }

    @RequestMapping(value = "getAll", method = RequestMethod.GET)
    @ResponseBody
    public String getAll() {
        List<ExpenseDetail> details = expenseService.getAll();
        String result = "" + details;
        System.out.println(details);
        return result;
    }
}