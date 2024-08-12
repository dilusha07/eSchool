import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { FooterComponent } from '../footer/footer.component';

@Component({
  selector: 'app-welcomepage',
  standalone: true,
  imports: [FooterComponent, RouterModule],
  templateUrl: './welcomepage.component.html',
  styleUrl: './welcomepage.component.css'
})
export class WelcomepageComponent implements OnInit{
  constructor(private _router : Router) { }


  ngOnInit(): void {}

  navigate()
  {
    this._router.navigate(['/login']);
  }

}
