import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  constructor(private httpCLient: HttpClient) { }

  checkIsValidUser(user:any){
    console.log("username pass has reached auth service");
    let loginUrl = "http://localhost:8081/login"
    let userJson = JSON.stringify(user);
    console.log(userJson);
    const body = new HttpParams()
      .set('email',user.email)
      .set('password',user.password)

      return this.httpCLient.post(loginUrl,body.toString(),{
        headers: new HttpHeaders().set('Content-type','application/x-www-form-urlencoded'),
        withCredentials:true
      })
    
    // return this.httpCLient.get("http://localhost:8081/login", user);
  }


  addUser(user:any){
    console.log("User Infor has reached auth service");
    let userJson = JSON.stringify(user);
    console.log(userJson);
    return this.httpCLient.post("http://localhost:8081/register", user);
    
  }

  // resetPassToken(token:any){
  //   console.log("Token recieved"+token);
  //   return this.httpCLient.post("http://localhost:8081/reset-password", {token});
  // }

  resetPassToken(data: any) {
    console.log("Token received:", data.token);
  
    return this.httpCLient.post(
      "http://localhost:8081/reset-password",
      data   // 👈 send EXACT object
    );
  }


  resetPassword(email:any){
    // console.log(email+"recived");
    return this.httpCLient.post("http://localhost:8081/forgot-password",{email});
  }
}
