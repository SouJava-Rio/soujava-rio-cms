export class Slide{

  id: string;
  title: string;
  description: string;
  disabled: boolean;
  order: number;

  constructor(id:string, title:string, description:string, disabled:boolean, order:number){
    this.id = id;
    this.title = title;
    this.description = description;
    this.disabled = disabled;
    this.order = order;
  }

}
