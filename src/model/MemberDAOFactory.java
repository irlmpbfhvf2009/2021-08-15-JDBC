package model;

	public class MemberDAOFactory {
		
		//singleton + factory design pattern (單例 + 工廠模式)
		
		private static final MemberDAO MEMBER_DAO = createMemberDAO();
		
		private static MemberDAO createMemberDAO() {
			MemberDAO mDAO =new MemberDAO();
			return mDAO;
		}

		public static MemberDAO getMemberDAO() {
			return MEMBER_DAO;
		}
	}


