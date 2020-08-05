/**
 * 회원가입시 입력항목의 유효성 확인 
 */
var join = {
	common:{
		empty:{ code: 'invalid', desc:'입력하세요'}
		, space: {code: 'invalid', desc: '공백없이 입력하세요'}
		, min: {code: 'invalid', desc: '최소5자 이상 입력하세요'}
		, max: {code: 'invalid', desc: '최대20자 이하 입력하세요'}
	}
	, u_userid: {
		empty:{ code: 'invalid', desc:'아이디를 입력하세요!'}
		, invalid: {code: 'invalid', desc: '이메일 형식으로 입력해주세요' }
		, valid: {code: 'valid', desc: '아이디 중복확인하세요' }
		, usable: {code:'valid', desc: '사용가능한 아이디입니다'}
		, unusable: {code:'invalid', desc: '이미사용중인 아이디입니다'}
	}
	, u_nick: {
		invalid: {code: 'invalid', desc: '닉네임을 입력하세요' }
		, valid: {code: 'valid', desc: '닉네임 중복확인하세요' }
		, usable: {code:'valid', desc: '사용가능한 닉네임입니다'}
		, unusable: {code:'invalid', desc: '이미사용중인 닉네임입니다'}
	}
	, u_userpw: {
		invalid: {code:'invalid', desc:'영문 대/소문자, 숫자만 입력하세요'}
		, valid: {code:'valid', desc:'사용가능한 비밀번호입니다'}
		, lack: {code:'invalid', desc:'영문 대/소문자, 숫자를 모두 포함해야 합니다'}
		, equal: {code:'valid', desc:'비밀번호가 일치합니다'}
		, notEqual: {code:'invalid', desc:'비밀번호가 일치하지 않습니다'}
	}
	, tag_status: function(tag){
		var data = tag.val();
		tag = tag.attr('name');
		if(tag=='u_userid') 				data = this.u_userid_status(data);
		else if(tag =='u_nick')		data = this.u_nick_status(data);
		else if(tag=='u_userpw')			data = this.u_userpw_status(data);
		else if(tag=='u_userpw_ck')		data = this.u_userpw_ck_status(data);
		return data;
	}
	, id_usable:function(data){
		if(data)	 return this.u_userid.usable /*data 값이 true일경우*/
		else		return this.u_userid.unusable;/*data 값이 false일경우*/
	}
	, nick_usable:function(data){
		if(data)	 return this.u_nick.usable /*data 값이 true일경우*/
		else		return this.u_nick.unusable;/*data 값이 false일경우*/
	}
	
	
	
	, u_userpw_ck_status: function(pw_ck){
		if(pw_ck=='')				return this.common.empty;
		else if (pw_ck == $('[name=u_userpw]').val())	
					return this.u_userpw.equal;
		else		return this.u_userpw.notEqual
	}
	
	, u_userpw_status: function(pw){
		var reg = /[^a-zA-Z0-9]/g;
		var upper = /[A-Z]/g, lower = /[a-z]/g, digit = /[0-9]/g;
		if(pw=='')					return this.common.empty;
		else if(pw.match(space)) 	return this.common.space;
		else if(reg.test(pw))		return this.u_userpw.invalid;
		else if(pw.length<5) 		return this.common.min;
		else if(pw.length>20) 		return this.common.max;
		else if(!upper.test(pw) || !lower.test(pw) || !digit.test(pw))	
									return this.u_userpw.lack;
		else 						return this.u_userpw.valid;
	}
	, u_userid_status: function(id){
		var reg = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
		if( id=='') return this.u_userid.empty;/*빈칸검사*/
		else if(id.match(space)) return this.common.space;/*공백입력검사*/
		else if(!reg.test(id)) return this.u_userid.invalid; /*아이디검사*/
		else 					return this.u_userid.valid;
		
	}
	, u_nick_status: function(nick){
		if( nick=='') return this.u_nick.invalid;/*빈칸검사*/
		else if(nick.match(space)) return this.common.space;/*공백입력검사*/
		else return this.u_nick.valid;
		
	}
	
	
	
	
	
};
var space = /\s/g;


