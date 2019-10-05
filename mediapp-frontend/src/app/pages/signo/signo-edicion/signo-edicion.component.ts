import { MatSnackBar, MatDialog } from '@angular/material';
import { Paciente } from 'src/app/_model/paciente';
import { FormGroup, FormControl } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { PacienteService } from 'src/app/_service/paciente.service';
import { SignoService } from 'src/app/_service/signo.service';
import { Signo } from 'src/app/_model/signo';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { switchMap } from 'rxjs/operators';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { PacienteDialogoComponent } from '../../paciente/paciente-dialogo/paciente-dialogo.component';

@Component({
  selector: 'app-signo-edicion',
  templateUrl: './signo-edicion.component.html',
  styleUrls: ['./signo-edicion.component.css']
})
export class SignoEdicionComponent implements OnInit {

  form: FormGroup;
  pacientes: Paciente[] = [];
  formControlPaciente: FormControl = new FormControl();
  pacienteSeleccionado: Paciente = null;  
  maxFecha: Date = new Date();
  filteredOptions: Observable<any[]>
  id: number;
  signo: Signo;  
  edicion: boolean = false;


  constructor(private pacienteService: PacienteService, private signoService: SignoService, private snackBar: MatSnackBar, private route: ActivatedRoute, private router: Router, private dialog: MatDialog) { 
    this.signo = new Signo();
    this.form = new FormGroup({
      'id': new FormControl(0),
      'paciente': this.formControlPaciente,
      'fecha': new FormControl(new Date()),
      'temperatura': new FormControl(''),
      'pulso': new FormControl(''),
      'ritmoRespiratorio': new FormControl('')
    });
  }
  

  ngOnInit() {    
    this.route.params.subscribe((params: Params) => {
      this.id = params['id'];

      this.edicion = params['id'] != null;
      this.initForm();
    });
    this.listarPacientes();
    this.filteredOptions = this.formControlPaciente.valueChanges.pipe(map(val => this.filter(val)));

    this.pacienteService.pacienteNuevo.subscribe(data => {
      //console.log("paciente nuevo en signo", data);
      this.form.patchValue({
        paciente: data      
      });
      //this.pacienteSeleccionado = data;
    });
  }

  initForm() {
    if (this.edicion) {
      this.signoService.listarPorId(this.id).subscribe(data => {
        let id = data.idSigno;
        let temperatura = data.temperatura;
        let pulso = data.pulso;
        let ritmoRespiratorio = data.ritmoRespiratorio;
        let fecha = new Date(data.fecha);                
        let paciente = data.paciente;
        //this.pacienteSeleccionado = paciente;

        this.form = new FormGroup({
          'id': new FormControl(id),
          'paciente': new FormControl(paciente),
          'fecha': new FormControl(fecha),
          'temperatura': new FormControl(temperatura),
          'pulso': new FormControl(pulso),
          'ritmoRespiratorio': new FormControl(ritmoRespiratorio)
        });
      });
    }
  }



  listarPacientes() {
    this.pacienteService.listar().subscribe(data => {
      this.pacientes = data;
    });
  }

  filter(val: any) {
    if (val != null && val.idPaciente > 0) {
      return this.pacientes.filter(option =>
        option.nombres.toLowerCase().includes(val.nombres.toLowerCase()) || option.apellidos.toLowerCase().includes(val.apellidos.toLowerCase()) || option.dni.includes(val.dni));
    } else {
      return this.pacientes.filter(option =>
        option.nombres.toLowerCase().includes(val.toLowerCase()) || option.apellidos.toLowerCase().includes(val.toLowerCase()) || option.dni.includes(val));
    }
  }

  displayFn(val: Paciente) {
    return val ? `${val.nombres} ${val.apellidos}` : val;
  }

  seleccionarPaciente(e: any) {
    this.pacienteSeleccionado = e.option.value;
  }

  estadoBotonRegistrar() {
    return (this.pacienteSeleccionado === null) ;
  }

  aceptar() {
    this.signo.idSigno = this.form.value['id'];
    this.signo.paciente = this.pacienteSeleccionado;
    this.signo.pulso = this.form.value['pulso'];
    this.signo.ritmoRespiratorio = this.form.value['ritmoRespiratorio'];
    this.signo.temperatura = this.form.value['temperatura'];     
    let fecha = this.form.value['fecha'];    
    var tzoffset = (this.form.value['fecha']).getTimezoneOffset() * 60000;
    var localISOTime = (new Date(this.form.value['fecha'] - tzoffset)).toISOString();
    //var localISOTime = (new Date(Date.now() - tzoffset)).toISOString();   
    this.signo.fecha = localISOTime;

    if (this.signo != null && this.signo.idSigno > 0) {
      this.signoService.modificar(this.signo).pipe(switchMap(() => {
        return this.signoService.listar();
      })).subscribe(data => {
        this.signoService.signoCambio.next(data);
        this.signoService.mensajeCambio.next("Se modifico");
      });
    } else {
      this.signoService.registrar(this.signo).subscribe(data => {
        this.signoService.listar().subscribe(especialidad => {
          this.signoService.signoCambio.next(especialidad);
          this.signoService.mensajeCambio.next("Se registro");
        });
      });
    }

    this.router.navigate(['signo']);
  }

  openDialog() {    
    this.dialog.open(PacienteDialogoComponent, {
      width: '350px'      
    });
  }

}


