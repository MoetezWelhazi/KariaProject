import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { KariaUserService } from '../service/karia-user.service';
import { IKariaUser } from '../karia-user.model';

import { KariaUserFormService } from './karia-user-form.service';

import { KariaUserUpdateComponent } from './karia-user-update.component';

describe('KariaUser Management Update Component', () => {
  let comp: KariaUserUpdateComponent;
  let fixture: ComponentFixture<KariaUserUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let kariaUserFormService: KariaUserFormService;
  let kariaUserService: KariaUserService;
  let userService: UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), KariaUserUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(KariaUserUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(KariaUserUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    kariaUserFormService = TestBed.inject(KariaUserFormService);
    kariaUserService = TestBed.inject(KariaUserService);
    userService = TestBed.inject(UserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const kariaUser: IKariaUser = { id: 456 };
      const user: IUser = { id: 15077 };
      kariaUser.user = user;

      const userCollection: IUser[] = [{ id: 18948 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ kariaUser });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining),
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const kariaUser: IKariaUser = { id: 456 };
      const user: IUser = { id: 23777 };
      kariaUser.user = user;

      activatedRoute.data = of({ kariaUser });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.kariaUser).toEqual(kariaUser);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKariaUser>>();
      const kariaUser = { id: 123 };
      jest.spyOn(kariaUserFormService, 'getKariaUser').mockReturnValue(kariaUser);
      jest.spyOn(kariaUserService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kariaUser });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: kariaUser }));
      saveSubject.complete();

      // THEN
      expect(kariaUserFormService.getKariaUser).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(kariaUserService.update).toHaveBeenCalledWith(expect.objectContaining(kariaUser));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKariaUser>>();
      const kariaUser = { id: 123 };
      jest.spyOn(kariaUserFormService, 'getKariaUser').mockReturnValue({ id: null });
      jest.spyOn(kariaUserService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kariaUser: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: kariaUser }));
      saveSubject.complete();

      // THEN
      expect(kariaUserFormService.getKariaUser).toHaveBeenCalled();
      expect(kariaUserService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKariaUser>>();
      const kariaUser = { id: 123 };
      jest.spyOn(kariaUserService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kariaUser });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(kariaUserService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareUser', () => {
      it('Should forward to userService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
