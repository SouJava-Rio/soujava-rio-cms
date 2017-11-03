import { Component, OnInit } from '@angular/core';
import { Slide } from '../slide';
import {AjaxMessageResponse} from '../ajaxMessageResponse';
import { SlideService } from '../slide.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-slide-list',
  templateUrl: './slide-list.component.html',
  styleUrls: ['./slide-list.component.css'],
  providers: [SlideService]
})
export class SlideListComponent implements OnInit {

  private slides: Slide[] = [];

    constructor(private router: Router, private slideService: SlideService) { }

    ngOnInit() { // when component loading get all users and set the users[]
      this.getAllSlides();
    }

    getAllSlides() {
      this.slideService.findAll().subscribe(
        listSlidesResponse => {
          this.slides =  listSlidesResponse.data as Slide[];
        },
        err => {
          console.log(err);
        }

      );
    }

    redirectNewSlidePage() {
      this.router.navigate(['/slide/create']);
    }

    editSlidePage(slide: Slide) {
      if (slide) {
        this.router.navigate(['/slide/edit', slide.id]);
      }
    }

    deleteSlide(slide: Slide) {
      if (slide) {
        this.slideService.deleteSlideById(slide.id).subscribe(
          res => {
            this.getAllSlides();
            this.router.navigate(['/slide']);
            console.log('done');
          }
        );
      }
    }

}
