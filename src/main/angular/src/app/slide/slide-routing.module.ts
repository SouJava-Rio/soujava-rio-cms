import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SlideListComponent } from './slide-list/slide-list.component';
import { SlideFormComponent } from './slide-form/slide-form.component';

const routes: Routes = [
  {path: 'slide', component: SlideListComponent},
  {path: 'slide/create', component: SlideFormComponent},
  {path: 'slide/edit/:id', component: SlideFormComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SlideRoutingModule { }
