package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class CarRepository implements BaseRepository<Car> {
  private List<Car> carData = new ArrayList<>();

  public Car create(Car car) {
    carData.add(car);
    return car;
  }

  public Iterator<Car> findAll() {
    return carData.iterator();
  }

  public Car findById(String id) {
    for (Car car : carData) {
      if (car.getCarId().equals(id)) {
        return car;
      }
    }
    return null;
  }

  public Car update(Car updatedCar) {
    String id = updatedCar.getCarId();
    Car carInRepository = this.findById(id);

    if (carInRepository == null) {
      return null;
    }

    int updatedCarQuantity = updatedCar.getCarQuantity();
    if (updatedCarQuantity <= 0)
      updatedCar.setCarQuantity(0);
    else
      updatedCar.setCarQuantity(updatedCar.getCarQuantity());

    carInRepository.setCarName(updatedCar.getCarName());
    carInRepository.setCarColor(updatedCar.getCarColor());
    return carInRepository;
  }

  public Car delete(String id) {
    Car deletedCar = this.findById(id);
    carData.remove(deletedCar);
    return deletedCar;
  }
}
