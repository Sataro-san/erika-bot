package kg.shsatarov.erikabot.services;

import net.dv8tion.jda.api.entities.Member;

public interface UserService {

    Boolean hasSalaryRole(Member member);

}
