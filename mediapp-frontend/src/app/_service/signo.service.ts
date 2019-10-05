import { FiltroConsultaDTO } from './../_dto/filtroConsultaDTO';
import { HttpClient } from '@angular/common/http';
import { Subject } from 'rxjs';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Signo } from '../_model/signo';

@Injectable({
  providedIn: 'root'
})
export class SignoService {
  
  signoCambio = new Subject<Signo[]>();
  mensajeCambio = new Subject<string>();

  url: string = `${environment.HOST}/signos`;
  
  constructor(private http: HttpClient) { }
  
  listar() {
    return this.http.get<Signo[]>(this.url);
  }

  listarPageable(p: number, s: number) {
    return this.http.get<any>(`${this.url}/pageable?page=${p}&size=${s}`);
  }

  listarPorId(idSigno: number) {
    return this.http.get<Signo>(`${this.url}/${idSigno}`);
  }

  registrar(Signo: Signo) {
    return this.http.post(this.url, Signo);
  }

  modificar(Signo: Signo) {
    return this.http.put(this.url, Signo);
  }

  eliminar(idSigno: number) {
    return this.http.delete(`${this.url}/${idSigno}`);
  }

  buscar(filtroConsulta: FiltroConsultaDTO) {
    return this.http.post<Signo[]>(`${this.url}/buscar`, filtroConsulta);
  }

}
