package alpizar.alfredo.capgeminicasestudy.controller;

import java.security.Principal;
import alpizar.alfredo.capgeminicasestudy.dao.ProductDAO;
import alpizar.alfredo.capgeminicasestudy.dao.RoleDAO;
import alpizar.alfredo.capgeminicasestudy.model.UserApp;
import alpizar.alfredo.capgeminicasestudy.model.Account;

import alpizar.alfredo.capgeminicasestudy.dao.UserDAO;
import javax.validation.Valid;
import alpizar.alfredo.capgeminicasestudy.utils.WebUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.time.Instant;

@Controller
public class Main {

    @Autowired
    private UserDAO uDAO;

    @Autowired
    private RoleDAO rDAO;

    @Autowired
    private ProductDAO pDAO;

    private String userName;

    @GetMapping({"/home", "/"})
    public String homePage(Model model, Principal principal) {


        userName = principal.getName();

        System.out.println("User Name: " + userName);





        List<Account> accounts =pDAO.getAccounts(uDAO.findUserAccount(userName).getUserId());

        model.addAttribute(accounts);
        model.addAttribute("username", userName);


        return "home";
    }


    @GetMapping("/apply")
    public String applyPage(Model model) {
        model.addAttribute(new Account());
        return "apply";
    }

    @PostMapping("/apply")
    public String addAccount( @ModelAttribute("userAcc")Account acc) {
        pDAO.openAccount(userName, acc.getBalance(), acc.getProductID() );
        return "home";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {

        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute(new UserApp());
        return "register";
    }

    @PostMapping ("/register")
    public String registerSubmit(@Valid UserApp validateForm, BindingResult bindingResult, @ModelAttribute("UserApp")UserApp user, HttpServletRequest request, Model model, @RequestParam("product") Long product, @RequestParam("deposit") Long deposit) {

        if (bindingResult.hasErrors()) {
            return "register";
        }else {
            uDAO.registerInsert(user);
            rDAO.registerUserRoles(user.getUserName());
            pDAO.openAccount(user.getUserName(), deposit, product);
            this.doUpload(request, model, user);

            return "login";
        }
    }


    @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        return "logoutSuccessfulPage";
    }



    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();

            String userInfo = WebUtils.toString(loginedUser);

            model.addAttribute("userInfo", userInfo);

            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);

        }

        return "403Page";
    }
    private String doUpload(HttpServletRequest request, Model model, UserApp myUploadForm) {


        String uploadRootPath = request.getServletContext().getRealPath("upload");
        System.out.println("uploadRootPath=" + uploadRootPath);

        File uploadRootDir = new File(uploadRootPath);

        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        MultipartFile[] fileDatas = myUploadForm.getDataFile();

        List<File> uploadedFiles = new ArrayList<File>();
        List<String> failedFiles = new ArrayList<String>();

        for (MultipartFile fileData : fileDatas) {

            String name = fileData.getOriginalFilename();
            System.out.println("Client File Name = " + name);

            if (name != null && name.length() > 0) {
                try {
                    File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);

                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                    stream.write(fileData.getBytes());
                    stream.close();

                    uploadedFiles.add(serverFile);
                    System.out.println("Write file: " + serverFile);
                } catch (Exception e) {
                    System.out.println("Error Write file: " + name);
                    failedFiles.add(name);
                }
            }
        }
        model.addAttribute("uploadedFiles", uploadedFiles);
        model.addAttribute("failedFiles", failedFiles);
        return "register";
    }


}