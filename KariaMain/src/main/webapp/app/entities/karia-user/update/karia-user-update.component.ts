import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { Gender } from 'app/entities/enumerations/gender.model';
import { RoleEnum } from 'app/entities/enumerations/role-enum.model';
import { KariaUserService } from '../service/karia-user.service';
import { IKariaUser } from '../karia-user.model';
import { KariaUserFormService, KariaUserFormGroup } from './karia-user-form.service';

@Component({
  standalone: true,
  selector: 'jhi-karia-user-update',
  templateUrl: './karia-user-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class KariaUserUpdateComponent implements OnInit {
  isSaving = false;
  kariaUser: IKariaUser | null = null;
  genderValues = Object.keys(Gender);
  roleEnumValues = Object.keys(RoleEnum);

  usersSharedCollection: IUser[] = [];

  editForm: KariaUserFormGroup = this.kariaUserFormService.createKariaUserFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected kariaUserService: KariaUserService,
    protected kariaUserFormService: KariaUserFormService,
    protected userService: UserService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kariaUser }) => {
      this.kariaUser = kariaUser;
      if (kariaUser) {
        this.updateForm(kariaUser);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('kariaMainApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const kariaUser = this.kariaUserFormService.getKariaUser(this.editForm);
    if (kariaUser.id !== null) {
      this.subscribeToSaveResponse(this.kariaUserService.update(kariaUser));
    } else {
      this.subscribeToSaveResponse(this.kariaUserService.create(kariaUser));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKariaUser>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(kariaUser: IKariaUser): void {
    this.kariaUser = kariaUser;
    this.kariaUserFormService.resetForm(this.editForm, kariaUser);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, kariaUser.user);
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.kariaUser?.user)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }
}
