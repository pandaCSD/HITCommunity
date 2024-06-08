export class User {
    constructor(uid, uname, upassword) {
      this.uid = uid;
      this.uname = uname;
      this.upassword = upassword;
    }
  
    // 方法：打印用户信息
    printUserInfo() {
      console.log(`用户ID: ${this.uid}, 用户名: ${this.uname}`);
    }
  
    // 静态方法：验证用户信息
    static validateUser(user) {
      // 验证逻辑，例如检查用户名和密码的格式是否正确
      return user.uname.length > 0 && user.upassword.length > 6;
    }
  }