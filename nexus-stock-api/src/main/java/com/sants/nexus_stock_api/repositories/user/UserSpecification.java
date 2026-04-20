package com.sants.nexus_stock_api.repositories.user;

import org.springframework.data.jpa.domain.Specification;

import com.sants.nexus_stock_api.domain.user.User;
import com.sants.nexus_stock_api.domain.user.UserRole;

public class UserSpecification {
    public static Specification<User> hasName(String name) {
            return (root, query, cb) ->
                    name == null ? null :
                            cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        }

        public static Specification<User> hasEmail(String email) {
            return (root, query, cb) ->
                    email == null ? null :
                            cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
        }

        public static Specification<User> hasRole(UserRole role) {
            return (root, query, cb) ->
                    role == null ? null :
                            cb.equal(root.get("role"), role);
        }

        public static Specification<User> hasActive(Boolean active) {
            return (root, query, cb) ->
                    active == null ? null :
                            cb.equal(root.get("active"), active);
        }
}
