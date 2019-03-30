package io.raycom.modules.sys.user;

import io.raycom.web.service.BaseService;
import io.raycom.utils.string.StringUtils;
import io.raycom.core.collection.RData;
import io.raycom.web.bean.Page;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService extends BaseService {
	
	@Autowired
	private UserDao userdao;
	
	public void getAllUser(Page<RData> page){
		ArrayList<RData> rList = userdao.getAllUser(page);
		page.setData(rList);
	}
	
	/**
	 * 添加用户角色
	 */
	public void createUserRole(RData rdata){
		String roleString = rdata.getString("arrayRole").toString();
		
		if(!StringUtils.isEmpty(roleString)){
			
			String roles[] =  roleString.split(",");
			for (String role : roles) {
				rdata.set("roleId",role);
				userdao.createUserRole(rdata);
			}
		}
	}
	
	public String createUserDo(RData rData){
		//校验用户名重复，则返回用户名重复，否则返回true;
		if(!isLoginNameExist(rData)){
			userdao.createUserDo(rData);
			createUserRole(rData);
		}else{
			return "登录名重复！";
		}
		return "true";
	}
	
	public boolean isLoginNameExist(RData rdata){
		return  userdao.getUser(rdata)!=null;
	}
	
	public void updateUserDo(RData rData){
		userdao.updateUser(rData);
		userdao.deleteUserRole(rData);
		createUserRole(rData);
	}
	
	public ArrayList<RData> getOfficeList(){
		return userdao.getOfficeList();
	}
	
	public ArrayList<RData> getUserRole(RData rdata){
		return userdao.getUserRole(rdata);
	}
	public ArrayList<RData> getRoleList(){
		return userdao.getRoleList();
	}
	
	public ArrayList<RData> getExceptRoleList(RData rdata){
		return userdao.getExceptRoleList(rdata);
	}
	
	public RData getUser(RData rdata){
		return userdao.getUser(rdata);
	}
	
	public RData checkLoginName(RData rdata) {
		return userdao.checkLoginName(rdata);
	}
	
	public void deleteUser(RData rData){
		userdao.deleteUser(rData);
	}
	
	public void deleteUserRole (RData rData){
		userdao.deleteUserRole(rData);
	}

	public String resetPwd(RData rdata) {
		userdao.resetPwd(rdata);
		return "ok";
	}
	
	public RData queryUserName(RData rdata) {
		return userdao.queryUserName(rdata);
	}
	
	public String saveUpdatedPwd(RData rdata) {
		//更新密码
		this.savePwd(rdata);
		return "ok";
	}
	
	private void savePwd(RData rdata) {
		userdao.savePwd(rdata);
	}
	
	public String queryUserPwd(RData rdata) {
		return userdao.queryUserPwd(rdata);
	}
}
