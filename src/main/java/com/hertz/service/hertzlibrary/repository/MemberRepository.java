package com.hertz.service.hertzlibrary.repository;

import com.hertz.service.hertzlibrary.model.Member;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Replacement for an actual repository.
 * To be read as
 * public class MemberRepository implements CrudRepository<Member, String>
 */

@Service
public class MemberRepository {

    private List<Member> members; // the members "table"

    @PostConstruct
    public void init() { // initial data for easier manual testing
        members = new ArrayList<>();
        members.add(new Member("aaa"));
        members.add(new Member("bbb"));
        members.add(new Member("ccc"));
    }

    public Optional<Member> findMember(final String name) {
        return members.stream()
                .filter(member -> Objects.equals(name, member.getName()))
                .findFirst();
    }

}
