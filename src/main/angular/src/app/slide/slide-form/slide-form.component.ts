import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {SlideService} from '../slide.service';
import {Slide} from '../slide';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-slide-form',
  templateUrl: './slide-form.component.html',
  styleUrls: ['./slide-form.component.css'],
  providers: [SlideService]
})
export class SlideFormComponent implements OnInit, OnDestroy {

  id: string;
  slide: Slide;

  slideForm: FormGroup;
  private sub: any;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private slideService: SlideService) { }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      this.id = params['id'];
    });

    this.slideForm = new FormGroup({
      title: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required)
    });

    if (this.id) { //edit form
      this.slideService.findById(this.id).subscribe(
        slide => {
            this.id = slide.id;
            this.slideForm.patchValue({
            title: slide.title,
            description: slide.description,
          });
         }, error => {
          console.log(error);
         }
      );

    }
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  onSubmit() {
     if (this.slideForm.valid) {

         if (this.id) {

           let slide: Slide = new Slide(this.id,
             this.slideForm.controls['title'].value,
             this.slideForm.controls['description'].value,
             true, 0
           );
           this.slideService.updateSlide(slide).subscribe();

         } else {

            let slide: Slide = new Slide(null,
              this.slideForm.controls['title'].value,
              this.slideForm.controls['description'].value,
              true, 0
            );
            this.slideService.saveSlide(slide).subscribe();

         }
      }
       this.slideForm.reset();
       this.router.navigate(['/slide']);
   }

   redirectSlidePage() {
     this.router.navigate(['/slide']);

   }

}
