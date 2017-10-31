import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {SlideService} from '../slide.service';
import {Slide} from '../slide';
import {AjaxMessageResponse} from '../ajaxMessageResponse';
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
  ajaxMessageResponse: AjaxMessageResponse<Slide>;
  slideForm: FormGroup;
  private sub: any;

  constructor(private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private slideService: SlideService) { 

    this.slideForm = formBuilder.group({
      title: ['', [
        Validators.required,
        Validators.minLength(30)
      ]],
      description: ['', [
        Validators.required,
        Validators.minLength(100)
      ]]
      //,      
      // email: ['', [
      //   Validators.required,
      //   //BasicValidators.email
      //   //Validators.pattern("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
      // ]],
      // phone: [],
      // address: formBuilder.group({
      //   street: ['', Validators.minLength(3)],
      //   suite: [],
      //   city: ['', Validators.maxLength(30)],
      //   zipcode: ['', Validators.pattern('^([0-9]){5}([-])([0-9]){4}$')]
      // })
    });
                  
  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      this.id = params['id'];
    });
    this.slideForm = new FormGroup({
      title: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required)
    });

    console.log(this.id);
    
    if (this.id) { //edit form
      this.slideService.findById(this.id).subscribe(
        ajaxMessageResponse => {
            this.id = ajaxMessageResponse.data.id;

            this.slideForm.patchValue({
            title: ajaxMessageResponse.data.title,
            description: ajaxMessageResponse.data.description
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
        var result;

        if (this.id) {

           let slide: Slide = new Slide(this.id,
             this.slideForm.controls['title'].value,
             this.slideForm.controls['description'].value,
             true, 0
           );
           result = this.slideService.updateSlide(slide).subscribe();

         } else {

            let slide: Slide = new Slide(null,
              this.slideForm.controls['title'].value,
              this.slideForm.controls['description'].value,
              true, 0
            );
            result = this.slideService.saveSlide(slide).subscribe();

         }
         this.slideForm.reset();
         result.subscribe(data => this.router.navigate(['slide']));

         //this.router.navigate(['/slide']);
  
      }
   }

   redirectSlidePage() {
     this.router.navigate(['/slide']);

   }

}
