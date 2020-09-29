package hello.core.member;

public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    /*
       MemberServiceImpl은 MemoryMemberRepository를 의존하지 않는다.
       단지 MemberREpository 인터페이스만 의존하는것이다. 어떤 구현 객체를 주입할지는
       오직 외부에서 결정된다.(AppConfig)
       이로써 관심사가 분리되었고 DIP가 완성되었다.
    * */
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
