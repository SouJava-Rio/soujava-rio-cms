import { Injectable } from '@angular/core';
import { Slide } from "./slide";
import { Http, Response } from "@angular/http";
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch';
import { Observable } from "rxjs/Observable";
@Injectable()
export class SlideService {

  private apiUrl = 'http://localhost:8080/api/v1.0/slides';

  constructor(private http: Http) {
  }

  findAll(): Observable<Slide[]> {
    return this.http.get(this.apiUrl)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

 findById(id: string): Observable<Slide> {
    return this.http.get(this.apiUrl + '/' + id)
      .map((res:Response) => res.json())
      .catch((error:any) => Observable.throw(error.json().error || 'Error'));
  }

  saveSlide(slide: Slide): Observable<Slide> {
    return this.http.post(this.apiUrl, slide)
    .catch((error:any) => Observable.throw(error.json().error || 'Server error'));

  }

  deleteSlideById(id: string): Observable<boolean> {
    return this.http.delete(this.apiUrl + '/' + id)
       .map((res:Response) => res.json())
       .catch((error:any) => Observable.throw(error.json().error || 'Server error'));

  }

  updateSlide(slide: Slide): Observable<Slide> {
      return this.http.put(this.apiUrl, slide)
        .map((res:Response) => res.json())
        .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
  }
}
