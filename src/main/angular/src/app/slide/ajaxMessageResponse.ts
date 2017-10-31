export class AjaxMessageResponse<T>{
    
    data: T;
      
    constructor(data:T){
        this.data = data;
    }
    
}    