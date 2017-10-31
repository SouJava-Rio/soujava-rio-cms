import { Injectable } from '@angular/core';
import { Slide } from './slide';
import { AjaxMessageResponse } from './ajaxMessageResponse';

import { Http, Response } from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class SlideService {

  private apiUrl = 'http://localhost:8080/api/slides';

  constructor(private http: Http) {
  }

  findAll(): Observable<AjaxMessageResponse<Slide[]>> {
    return this.http.get(this.apiUrl)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

 findById(id: string): Observable<AjaxMessageResponse<Slide>> {
    return this.http.get(this.apiUrl + '/' + id)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Error'));
  }

  saveSlide(slide: Slide): Observable<Slide> {
    return this.http.post(this.apiUrl, slide)
    .catch((error: any) => Observable.throw(error.json().error || 'Server error'));

  }

  deleteSlideById(id: string): Observable<boolean> {
    return this.http.delete(this.apiUrl + '/' + id)
       .map((res: Response) => res.json())
       .catch((error: any) => Observable.throw(error.json().error || 'Server error'));

  }

  updateSlide(slide: Slide): Observable<Slide> {
      return this.http.put(this.apiUrl + '/' + slide.id, slide)
        .map((res: Response) => res.json())
        .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }
}
