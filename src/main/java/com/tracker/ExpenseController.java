package com.tracker;

import com.google.gson.Gson;
import com.tracker.dto.ExpenseDetailDTO;
import com.tracker.model.ExpenseDetail;
import com.tracker.services.ExpenseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        model.addAttribute("expenseDetails", expenseDetails);
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
    public String addExpense(@ModelAttribute("expenseDetailDTO") ExpenseDetailDTO expenseDetailDTO, ModelMap model) {
        LOG.info("Request to add description : " + expenseDetailDTO.getDescription());
        expenseService.addExpense(expenseDetailDTO);

        List<ExpenseDetail> expenseDetails = expenseService.getAllForToday();
        LOG.info("Expense Details fetched : " + expenseDetails.size());
        model.addAttribute("expenseDetails", expenseDetails);
        return "index";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, Model model) {
        LOG.info("Request to delete for id : " + id);
        expenseService.delete(id);
        model.addAttribute("deleted", true);
        
        List<ExpenseDetail> expenseDetails = expenseService.getAllForToday();
        LOG.info("Expense Details fetched : " + expenseDetails.size());
        model.addAttribute("expenseDetails", expenseDetails);
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