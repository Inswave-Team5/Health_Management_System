package com.healthmanage.utils;

import java.time.Duration;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.healthmanage.model.Coupon;
import com.healthmanage.model.User;

public class Sort {

 public static Collection<Coupon> sortCoupon(Collection<Coupon> list) {
     return list.stream()
             .sorted(Comparator.comparing(Coupon::getNumber))
             .collect(Collectors.toList());
 }

 public static List<User> sortUser(Collection<User> list) {
     return list.stream()
             .sorted(Comparator.comparing(User::getName))
             .collect(Collectors.toList());
 }
 
 public static List<Duration> sortRank(Collection<Duration> list) {
    return list.stream()
          // a,b가 values값(시간)
            .sorted((a, b) -> a.compareTo(b))
            .collect(Collectors.toList());
 }

 public static Map<String, String> sortRank2(Map<String, String> list) {
    return list.entrySet().stream()
             .sorted(Map.Entry.comparingByValue())
             .collect(Collectors.toMap(
                     Map.Entry::getKey,
                     Map.Entry::getValue,
                     (e1, e2) -> e1,
                     LinkedHashMap::new
             ));
 }
}

