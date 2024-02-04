import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IProperty } from 'app/entities/property/property/property.model';
import { PropertyService } from 'app/entities/property/property/service/property.service';
import { IReview } from '../review.model';
import { ReviewService } from '../service/review.service';
import { ReviewFormService, ReviewFormGroup } from './review-form.service';

@Component({
  standalone: true,
  selector: 'jhi-review-update',
  templateUrl: './review-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ReviewUpdateComponent implements OnInit {
  isSaving = false;
  review: IReview | null = null;

  propertiesSharedCollection: IProperty[] = [];

  editForm: ReviewFormGroup = this.reviewFormService.createReviewFormGroup();

  constructor(
    protected reviewService: ReviewService,
    protected reviewFormService: ReviewFormService,
    protected propertyService: PropertyService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareProperty = (o1: IProperty | null, o2: IProperty | null): boolean => this.propertyService.compareProperty(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ review }) => {
      this.review = review;
      if (review) {
        this.updateForm(review);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const review = this.reviewFormService.getReview(this.editForm);
    if (review.id !== null) {
      this.subscribeToSaveResponse(this.reviewService.update(review));
    } else {
      this.subscribeToSaveResponse(this.reviewService.create(review));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReview>>): void {
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

  protected updateForm(review: IReview): void {
    this.review = review;
    this.reviewFormService.resetForm(this.editForm, review);

    this.propertiesSharedCollection = this.propertyService.addPropertyToCollectionIfMissing<IProperty>(
      this.propertiesSharedCollection,
      review.propertyId,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.propertyService
      .query()
      .pipe(map((res: HttpResponse<IProperty[]>) => res.body ?? []))
      .pipe(
        map((properties: IProperty[]) =>
          this.propertyService.addPropertyToCollectionIfMissing<IProperty>(properties, this.review?.propertyId),
        ),
      )
      .subscribe((properties: IProperty[]) => (this.propertiesSharedCollection = properties));
  }
}
