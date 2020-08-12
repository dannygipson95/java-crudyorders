package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.models.Payment;
import com.lambdaschool.javaorders.repositories.CustomerRepository;
import com.lambdaschool.javaorders.repositories.OrderRepository;
import com.lambdaschool.javaorders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
@Transactional
@Service(value = "customerService")
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerrepos;

    @Autowired
    PaymentRepository paymentrepos;

    @Override
    public List<Customer> findAllOrders() {
        List<Customer> allOrders = new ArrayList<>();
        customerrepos.findAll().iterator().forEachRemaining(allOrders::add);
        return allOrders;
    }

    @Override
    public Customer findCustomerById(long id) {
        return customerrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " Not Found!"));
    }

    @Override
    public List<Customer> findByNameLike(String custname) {
        List<Customer> list = new ArrayList<>();
        customerrepos.findByCustnameContainingIgnoringCase(custname).iterator().forEachRemaining(list::add);
        return list;
    }

    @Transactional
    @Override
    public Customer save(Customer customer) {
        Customer newCustomer = new Customer();

        if (customer.getCustcode() != 0){
            customerrepos.findById(customer.getCustcode())
                    .orElseThrow(() -> new EntityNotFoundException("Customer " + customer.getCustcode() + " Not Found"));

            newCustomer.setCustcode(customer.getCustcode());
        }

        newCustomer.setCustname(customer.getCustname());
        newCustomer.setCustcity(customer.getCustcity());
        newCustomer.setWorkingarea(customer.getWorkingarea());
        newCustomer.setCustcountry(customer.getCustcountry());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setReceiveamt(customer.getReceiveamt());
        newCustomer.setPaymentamt(customer.getPaymentamt());
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setPhone(customer.getPhone());
        newCustomer.setAgent(customer.getAgent());

        newCustomer.getOrders().clear();

        for (Order o: customer.getOrders()){
            Order newOrder = new Order();
            newOrder.setAdvanceamount(o.getAdvanceamount());
            newOrder.setOrdamount(o.getOrdamount());
            newOrder.setOrderdescription(o.getOrderdescription());
            newOrder.setCustomer(newCustomer);

            newOrder.getPayments().clear();
            for (Payment p: o.getPayments()){
                Payment newPayment = paymentrepos.findById(p.getPaymentid())
                        .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found!"));
                newOrder.getPayments().add(newPayment);
            }

            newCustomer.getOrders().add(o);
        }

        return customerrepos.save(newCustomer);
    }

    @Transactional
    @Override
    public Customer update(Customer inCustomer, long id) {
        Customer currentCustomer = customerrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " Not Found!"));

        if (inCustomer.getCustname() != null){
            currentCustomer.setCustname(inCustomer.getCustname());
        }
        if(inCustomer.getCustcity() != null) {
            currentCustomer.setCustcity(inCustomer.getCustcity());
        }
        if(inCustomer.getWorkingarea() != null) {
            currentCustomer.setWorkingarea(inCustomer.getWorkingarea());
        }
        if(inCustomer.getCustcountry() != null) {
            currentCustomer.setCustcountry(inCustomer.getCustcountry());
        }
        if(inCustomer.getGrade() != null) {
            currentCustomer.setGrade(inCustomer.getGrade());
        }
        if(inCustomer.hasvalueforopeningamt) {
            currentCustomer.setOpeningamt(inCustomer.getOpeningamt());
        }
        if(inCustomer.hasvalueforreceiveamt) {
            currentCustomer.setReceiveamt(inCustomer.getReceiveamt());
        }
        if(inCustomer.hasvalueforpaymentamt) {
            currentCustomer.setPaymentamt(inCustomer.getPaymentamt());
        }
        if(inCustomer.hasvalueforoutstandingamt) {
            currentCustomer.setOutstandingamt(inCustomer.getOutstandingamt());
        }
        if(inCustomer.getPhone() != null) {
            currentCustomer.setPhone(inCustomer.getPhone());
        }
        if(inCustomer.getAgent() != null) {
            currentCustomer.setAgent(inCustomer.getAgent());
        }

        if(inCustomer.getOrders().size() > 0) {
            for (Order o : inCustomer.getOrders()) {
                Order newOrder = new Order();
                newOrder.setAdvanceamount(o.getAdvanceamount());
                newOrder.setOrdamount(o.getOrdamount());
                newOrder.setOrderdescription(o.getOrderdescription());
                newOrder.setCustomer(currentCustomer);

                newOrder.getPayments().clear();
                for (Payment p : o.getPayments()) {
                    Payment newPayment = paymentrepos.findById(p.getPaymentid())
                            .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found!"));
                    newOrder.getPayments().add(newPayment);
                }

                currentCustomer.getOrders().add(o);
            }
        }
        return customerrepos.save(currentCustomer);
    }

    @Transactional
    @Override
    public void delete(long id) {
        if(customerrepos.findById(id).isPresent()){
            customerrepos.deleteById(id);
        }else{
            throw new EntityNotFoundException("Customer " + id + " Not Found");
        }
    }
}