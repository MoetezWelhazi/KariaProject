jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { KariaUserService } from '../service/karia-user.service';

import { KariaUserDeleteDialogComponent } from './karia-user-delete-dialog.component';

describe('KariaUser Management Delete Component', () => {
  let comp: KariaUserDeleteDialogComponent;
  let fixture: ComponentFixture<KariaUserDeleteDialogComponent>;
  let service: KariaUserService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, KariaUserDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(KariaUserDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(KariaUserDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(KariaUserService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
