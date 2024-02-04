import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProperty } from '../property.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../property.test-samples';

import { PropertyService } from './property.service';

const requireRestSample: IProperty = {
  ...sampleWithRequiredData,
};

describe('Property Service', () => {
  let service: PropertyService;
  let httpMock: HttpTestingController;
  let expectedResult: IProperty | IProperty[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PropertyService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Property', () => {
      const property = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(property).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Property', () => {
      const property = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(property).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Property', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Property', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Property', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPropertyToCollectionIfMissing', () => {
      it('should add a Property to an empty array', () => {
        const property: IProperty = sampleWithRequiredData;
        expectedResult = service.addPropertyToCollectionIfMissing([], property);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(property);
      });

      it('should not add a Property to an array that contains it', () => {
        const property: IProperty = sampleWithRequiredData;
        const propertyCollection: IProperty[] = [
          {
            ...property,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPropertyToCollectionIfMissing(propertyCollection, property);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Property to an array that doesn't contain it", () => {
        const property: IProperty = sampleWithRequiredData;
        const propertyCollection: IProperty[] = [sampleWithPartialData];
        expectedResult = service.addPropertyToCollectionIfMissing(propertyCollection, property);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(property);
      });

      it('should add only unique Property to an array', () => {
        const propertyArray: IProperty[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const propertyCollection: IProperty[] = [sampleWithRequiredData];
        expectedResult = service.addPropertyToCollectionIfMissing(propertyCollection, ...propertyArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const property: IProperty = sampleWithRequiredData;
        const property2: IProperty = sampleWithPartialData;
        expectedResult = service.addPropertyToCollectionIfMissing([], property, property2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(property);
        expect(expectedResult).toContain(property2);
      });

      it('should accept null and undefined values', () => {
        const property: IProperty = sampleWithRequiredData;
        expectedResult = service.addPropertyToCollectionIfMissing([], null, property, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(property);
      });

      it('should return initial array if no Property is added', () => {
        const propertyCollection: IProperty[] = [sampleWithRequiredData];
        expectedResult = service.addPropertyToCollectionIfMissing(propertyCollection, undefined, null);
        expect(expectedResult).toEqual(propertyCollection);
      });
    });

    describe('compareProperty', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProperty(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareProperty(entity1, entity2);
        const compareResult2 = service.compareProperty(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareProperty(entity1, entity2);
        const compareResult2 = service.compareProperty(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareProperty(entity1, entity2);
        const compareResult2 = service.compareProperty(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
