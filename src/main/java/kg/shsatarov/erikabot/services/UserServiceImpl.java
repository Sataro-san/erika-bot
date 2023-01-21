package kg.shsatarov.erikabot.services;

import net.dv8tion.jda.api.entities.Member;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Value("${constants.salaryRoleId}")
    private String salaryRoleId;

    @Override
    public Boolean hasSalaryRole(Member member) {
        return member.getRoles().stream().anyMatch(x -> x.getId().equals(salaryRoleId));
    }
}
