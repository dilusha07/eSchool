import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit{

  loggedUser = '';
  currRole = '';
  title = '';

  constructor(private activatedRoute: ActivatedRoute, private _router : Router) { }

  ngOnInit(): void 
  {
    this.loggedUser = JSON.stringify(sessionStorage.getItem('loggedUser')|| '{}');
    this.loggedUser = this.loggedUser.replace(/"/g, '');

    this.currRole = JSON.stringify(sessionStorage.getItem('ROLE')|| '{}'); 
    this.currRole = this.currRole.replace(/"/g, '');

    if(this.loggedUser === "admin1@gmail.com"){
      this.title = "Admin Dashboard";
    }
    else if(this.currRole === "professor"){
      this.title = "";
    }
    else if(this.currRole === "user"){
      this.title = "";
    }
  }

  logout()
  {
    sessionStorage.clear();
    this._router.navigate(['/login']);
  }

  navigateHome()
  {
    if(this.loggedUser === "admin@gmail.com"){
      this._router.navigate(['/admindashboard']);
    }
    else if(this.currRole === "professor"){
      this._router.navigate(['/professordashboard']);
    }
    else if(this.currRole === "user"){
      this._router.navigate(['/userdashboard']);
    }
  }

}
