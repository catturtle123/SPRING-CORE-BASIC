package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "hello.core.member", // 어디서 부터 찾는 지 지정
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
 ) // 원래는 안함. test code를 지키기 위함.
public class AutoAppConfig {


    @Bean(name = "memoryMemberRepository")
    public MemberRepository memberRepository() {
       return new MemoryMemberRepository();
    }
}