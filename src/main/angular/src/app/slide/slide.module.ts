import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SlideRoutingModule } from './slide-routing.module';
import { SlideListComponent } from './slide-list/slide-list.component';
import { SlideFormComponent } from './slide-form/slide-form.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    SlideRoutingModule,
    FormsModule,
    ReactiveFormsModule

  ],
  declarations: [SlideListComponent, SlideFormComponent]
})
export class SlideModule { }
