import { HttpResponse } from '@angular/common/http';
import { Paciente } from 'src/app/_model/paciente';
import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatSnackBar } from '@angular/material';
import { PacienteService } from 'src/app/_service/paciente.service';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-paciente-dialogo',
  templateUrl: './paciente-dialogo.component.html',
  styleUrls: ['./paciente-dialogo.component.css']
})
export class PacienteDialogoComponent implements OnInit {
  paciente: Paciente;

  constructor(private dialogRef: MatDialogRef<PacienteDialogoComponent>, private pacienteService: PacienteService, private snackBar: MatSnackBar) { }


  ngOnInit() {
    this.paciente = new Paciente();
  }

  cancelar() {
    this.dialogRef.close();
  }

  operar() {    
    this.pacienteService.registrar(this.paciente).pipe(switchMap(() => {
      return this.pacienteService.ultimo();
    })).subscribe(data => {    
      //console.log("paciente nuevo es ", data)  ;
      this.pacienteService.pacienteNuevo.next(data);    
      
    });
    this.dialogRef.close();

    // this.pacienteService.registrar(this.paciente).subscribe(
    //   () => {
    //     let paciente = this.pacienteService.ultimo();        
    //     this.pacienteService.pacienteNuevo.next(this.paciente);    
    // })
    
  }
}
