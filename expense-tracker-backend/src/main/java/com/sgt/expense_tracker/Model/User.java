package com.sgt.expense_tracker.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    int userId;
    String name;
    String username;
    String password;
    String email;
    String mobileNo;
    int activeYn;
}
