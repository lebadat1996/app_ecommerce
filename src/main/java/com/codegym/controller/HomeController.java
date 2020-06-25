package com.codegym.controller;


import com.codegym.model.*;
import com.codegym.service.product.IProduceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import com.codegym.service.approle.AppRoleService;
import com.codegym.service.customer.ICustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;


@Controller
@SessionAttributes("cart")
public class HomeController {

    @Autowired
    private IProduceService productService;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private AppRoleService appRoleService;

    @ModelAttribute("cart")
    public Cart setUpCart() {
        return new Cart();
    }

    @ModelAttribute("product")
    public Iterable<Product> products() {
        return productService.findAll();
    }

    @GetMapping("/loginForm")
    public ModelAndView showLoginForm() {
        ModelAndView modelAndView = new ModelAndView("loginForm");
        return modelAndView;
    }

    @GetMapping("/")
    public ModelAndView homePage() {
        ModelAndView modelAndView = new ModelAndView("index");
        List<Product> products = (List<Product>) productService.findAll();
        //  System.out.println(products.size());
        List<Product> featuredProducts = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            featuredProducts.add(products.get(i));
        }
//        System.out.println(featuredProducts.size());
        modelAndView.addObject("featuredProducts", featuredProducts);
        return modelAndView;
    }

    @GetMapping("/carts")
    public ModelAndView cartPage() {
        ModelAndView modelAndView = new ModelAndView("cart");
        return modelAndView;
    }

//    @GetMapping("/products")
//    public ModelAndView productPage() {
//        ModelAndView modelAndView = new ModelAndView("product");
//        modelAndView.addObject("products", produceService.findAll());
//        return modelAndView;
//    }

    @GetMapping("/products")
    public ModelAndView listProductPage(@RequestParam("s") Optional<String> s,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "6") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products;
        if (s.isPresent()) {
            products = productService.findAllByProductNameContaining(s.get(), pageable);
        } else {
            products = productService.findAllByType_NameOrderByPriceAsc("All", pageable);
        }
        ModelAndView modelAndView = new ModelAndView("product");
        Price price = new Price();
        modelAndView.addObject("price", price);
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/products-women")
    public ModelAndView listProductWomen(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "6") int size){
        Pageable pageable= PageRequest.of(page,size);
        Page<Product> productWomen ;
            productWomen = productService.findAllByType_NameOrderByPriceAsc("Women", pageable);
        ModelAndView modelAndView = new ModelAndView("productWomen");
        Price price = new Price();
        modelAndView.addObject("price", price);
        modelAndView.addObject("productWomen", productWomen);
        return modelAndView;
    }

    @GetMapping("/products-men")
    public ModelAndView listProductMen(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "6") int size){
        Pageable pageable= PageRequest.of(page,size);
        Page<Product> productMen ;
        productMen = productService.findAllByType_NameOrderByPriceAsc("Men",pageable);
        ModelAndView modelAndView = new ModelAndView("productMen");
        Price price = new Price();
        modelAndView.addObject("price", price);
        modelAndView.addObject("productMen",productMen);
        return modelAndView;
    }

    @GetMapping("/products-kid")
    public ModelAndView listProductKid(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "6") int size){
        Pageable pageable= PageRequest.of(page,size);
        Page<Product> productKid ;
        productKid = productService.findAllByType_NameOrderByPriceAsc("Kid",pageable);
        ModelAndView modelAndView = new ModelAndView("productKid");
        Price price = new Price();
        modelAndView.addObject("price", price);
        modelAndView.addObject("productKid",productKid);
        return modelAndView;
    }

    @GetMapping("/products-accesory")
    public ModelAndView listProductAccesory(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "6") int size){

        Pageable pageable= PageRequest.of(page,size);
        Page<Product> productAccesory ;
        productAccesory = productService.findAllByType_NameOrderByPriceAsc("Accessory",pageable);
        ModelAndView modelAndView = new ModelAndView("productAccesory");
        Price price = new Price();
        modelAndView.addObject("price", price);
        modelAndView.addObject("productAccesory",productAccesory);
        return modelAndView;
    }

    @GetMapping("/product-detail")
    public ModelAndView productDetailsPage() {
        ModelAndView modelAndView = new ModelAndView("product-detail");
        return modelAndView;
    }


    @GetMapping("/blogs")
    public ModelAndView details() {
        ModelAndView modelAndView = new ModelAndView("blog");
        return modelAndView;
    }

    @GetMapping("/abouts")
    public ModelAndView aboutPage() {
        ModelAndView modelAndView = new ModelAndView("about");
        return modelAndView;
    }

    @GetMapping("/contacts")
    public ModelAndView contactPage() {
        ModelAndView modelAndView = new ModelAndView("contact");
        return modelAndView;
    }


    @GetMapping("/blog-detail")
    public ModelAndView blogDetailsPage() {
        ModelAndView modelAndView = new ModelAndView("blog-detail");
        return modelAndView;
    }

    @GetMapping("/do_register")
    public ModelAndView showRegisterForm() {
        ModelAndView modelAndView = new ModelAndView("customer/registerForm");
        AppCustomer appCustomer = new AppCustomer();
        modelAndView.addObject("newCustomer", appCustomer);
        return modelAndView;
    }

    @PostMapping("/do_register")
    public ModelAndView createCustomer(@ModelAttribute("newCustomer") AppCustomer appCustomer) {
        AppRole appRole = appRoleService.getRoleById((long) 1);
        appCustomer.setAppRole(appRole);
        customerService.save(appCustomer);
        ModelAndView modelAndView = new ModelAndView("customer/registerForm");
        modelAndView.addObject("message", "Add new customer successfully!");
        return modelAndView;
    }

    @GetMapping("/customer_list")
    public ModelAndView showCustomerList() {
        Iterable<AppCustomer> customers = customerService.findAll();
        ModelAndView modelAndView = new ModelAndView("customer/customerList");
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping("/customer_edit/{id}")
    public ModelAndView showEditForm(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("customer/customerEdit");
        modelAndView.addObject("customer", customerService.getById(id));
        return modelAndView;
    }

    @PostMapping("/customer_edit")
    public String editCustomer(@ModelAttribute("customer") AppCustomer customer) {
        customerService.save(customer);
        return "redirect:/customer_list";
    }

    @GetMapping("/customer_delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("customer/customerDelete");
        modelAndView.addObject("customer", customerService.getById(id));
        return modelAndView;
    }

    @PostMapping("/customer_delete")
    public String deleteCustomer(@ModelAttribute("customer") AppCustomer customer) {
        customerService.remove(customer.getId());
        return "redirect:/customer_list";
    }

    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute AppCustomer customer){
        Iterable<AppCustomer> customers = customerService.findAll();
        for (AppCustomer customer1 : customers){
            if (customer.getUsername().equals(customer1.getUsername()) && customer.getPassword().equals(customer1.getPassword())){
                ModelAndView modelAndView = new ModelAndView("bill");
               AppCustomer customer2 = customerService.getCustomerByName(customer.getUsername());
               customer.setId(customer2.getId());
               customer.setUsername(customer2.getUsername());
               customer.setPassword(customer2.getPassword());
               customer.setPhone(customer2.getPhone());
               customer.setAddress(customer2.getAddress());
                modelAndView.addObject("customer", customer);
                return modelAndView;
            }
        }
        ModelAndView modelAndView = new ModelAndView("login");
        return modelAndView;
    }

    @PostMapping("/products-price")
    public ModelAndView showAllPrice(@ModelAttribute("price") Price price,
                                     @ModelAttribute()
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "6") int size){
        float lowPrice = 0;
        float highPrice = 0;
        if (price.getPrice().equals("Price")){
            lowPrice = (float) 0.0;
            highPrice = (float) 100000;
        } else if (price.getPrice().equals("0.00-50.00")){
            lowPrice = (float) 0.0;
            highPrice = (float) 50;
        } else if(price.getPrice().equals("50.00-100.00")){
            lowPrice = (float) 50;
            highPrice = (float) 100;
        } else if (price.getPrice().equals("100.00-150.00")){
            lowPrice = (float) 100;
            highPrice = (float) 150;
        } else if (price.getPrice().equals("150.00-200.00")){
            lowPrice = (float) 150;
            highPrice = (float) 200;
        } else if (price.getPrice().equals("200.00+")){
            lowPrice = (float) 200;
            highPrice = (float) 100000;
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = null;

        if (price.getSort().equals("Default Sorting")){
            products = productService.findAllByType_NameAndPriceBetween("All",lowPrice,highPrice, pageable);
        } else if (price.getSort().equals("Price: low to high")){
            products = productService.findAllByType_NameAndPriceBetweenOrderByPrice("All", lowPrice, highPrice, pageable);
        } else if (price.getSort().equals("Price: high to low")){
            products = productService.findAllByType_NameAndPriceBetweenOrderByPriceDesc("All", highPrice, lowPrice, pageable);
        }
//        products = produceService.findAllByType_NameAndPriceBetween("All", lowPrice, highPrice, pageable );
        ModelAndView modelAndView = new ModelAndView("product");
        modelAndView.addObject("products",products);
        return modelAndView;
    }

    @PostMapping("/products-women-price")
    public ModelAndView showWomenPrice(@ModelAttribute("price") Price price, @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "6") int size){
        float lowPrice = 0;
        float highPrice = 0;
        if (price.getPrice().equals("Price")){
            lowPrice = (float) 0.0;
            highPrice = (float) 100000;
        } else if (price.getPrice().equals("0.00-50.00")){
            lowPrice = (float) 0.0;
            highPrice = (float) 50;
        } else if(price.getPrice().equals("50.00-100.00")){
            lowPrice = (float) 50;
            highPrice = (float) 100;
        } else if (price.getPrice().equals("100.00-150.00")){
            lowPrice = (float) 100;
            highPrice = (float) 150;
        } else if (price.getPrice().equals("150.00-200.00")){
            lowPrice = (float) 150;
            highPrice = (float) 200;
        } else if (price.getPrice().equals("200.00+")){
            lowPrice = (float) 200;
            highPrice = (float) 100000;
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productWomen;
        productWomen = productService.findAllByType_NameAndPriceBetween("Women", lowPrice, highPrice, pageable );
        ModelAndView modelAndView = new ModelAndView("productWomen");
        modelAndView.addObject("productWomen",productWomen);
        return modelAndView;
    }

    @PostMapping("/products-men-price")
    public ModelAndView showMenPrice(@ModelAttribute("price") Price price, @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "6") int size){
        float lowPrice = 0;
        float highPrice = 0;
        if (price.getPrice().equals("Price")){
            lowPrice = (float) 0.0;
            highPrice = (float) 100000;
        } else if (price.getPrice().equals("0.00-50.00")){
            lowPrice = (float) 0.0;
            highPrice = (float) 50;
        } else if(price.getPrice().equals("50.00-100.00")){
            lowPrice = (float) 50;
            highPrice = (float) 100;
        } else if (price.getPrice().equals("100.00-150.00")){
            lowPrice = (float) 100;
            highPrice = (float) 150;
        } else if (price.getPrice().equals("150.00-200.00")){
            lowPrice = (float) 150;
            highPrice = (float) 200;
        } else if (price.getPrice().equals("200.00+")){
            lowPrice = (float) 200;
            highPrice = (float) 100000;
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productMen;
        productMen = productService.findAllByType_NameAndPriceBetween("Men", lowPrice, highPrice, pageable );
        ModelAndView modelAndView = new ModelAndView("productMen");
        modelAndView.addObject("productMen",productMen);
        return modelAndView;
    }

    @PostMapping("/products-kid-price")
    public ModelAndView showKidPrice(@ModelAttribute("price") Price price, @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "6") int size){
        float lowPrice = 0;
        float highPrice = 0;
        if (price.getPrice().equals("Price")){
            lowPrice = (float) 0.0;
            highPrice = (float) 100000;
        } else if (price.getPrice().equals("0.00-50.00")){
            lowPrice = (float) 0.0;
            highPrice = (float) 50;
        } else if(price.getPrice().equals("50.00-100.00")){
            lowPrice = (float) 50;
            highPrice = (float) 100;
        } else if (price.getPrice().equals("100.00-150.00")){
            lowPrice = (float) 100;
            highPrice = (float) 150;
        } else if (price.getPrice().equals("150.00-200.00")){
            lowPrice = (float) 150;
            highPrice = (float) 200;
        } else if (price.getPrice().equals("200.00+")){
            lowPrice = (float) 200;
            highPrice = (float) 100000;
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productKid;
        productKid = productService.findAllByType_NameAndPriceBetween("Kid", lowPrice, highPrice, pageable );
        ModelAndView modelAndView = new ModelAndView("productKid");
        modelAndView.addObject("productKid",productKid);
        return modelAndView;
    }

    @PostMapping("/products-accesory-price")
    public ModelAndView showAccesoryPrice(@ModelAttribute("price") Price price, @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "6") int size){
        float lowPrice = 0;
        float highPrice = 0;
        if (price.getPrice().equals("Price")){
            lowPrice = (float) 0.0;
            highPrice = (float) 100000;
        } else if (price.getPrice().equals("0.00-50.00")){
            lowPrice = (float) 0.0;
            highPrice = (float) 50;
        } else if(price.getPrice().equals("50.00-100.00")){
            lowPrice = (float) 50;
            highPrice = (float) 100;
        } else if (price.getPrice().equals("100.00-150.00")){
            lowPrice = (float) 100;
            highPrice = (float) 150;
        } else if (price.getPrice().equals("150.00-200.00")){
            lowPrice = (float) 150;
            highPrice = (float) 200;
        } else if (price.getPrice().equals("200.00+")){
            lowPrice = (float) 200;
            highPrice = (float) 100000;
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productAccesory;
        productAccesory = productService.findAllByType_NameAndPriceBetween("Accesory", lowPrice, highPrice, pageable );
        ModelAndView modelAndView = new ModelAndView("productAccesory");
        modelAndView.addObject("productAccesory",productAccesory);
        return modelAndView;
    }
}
