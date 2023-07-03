package com.cg.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "full_name", nullable = false) //tên cột và không null
    private String fullName;
    @Column(name = "email", nullable = false)
    private String email;
    private String phone;
    private String address;
    @Column(precision = 10, scale = 0, nullable = false) // scale=0 ko lấy giá trị sau dấu ,
    private BigDecimal balance;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer") //khi find sẽ không lấy các đối tượng liên quan
    private List<Deposits> deposits;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer") //khi find sẽ không lấy các đối tượng liên quan
    private List<Withdraw> withdraws;


    public Customer() {
    }

    public Customer(Long id, String fullName, String email, String phone, String address) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public Customer(Long id, String fullName, String email, String phone, String address, BigDecimal balance) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
